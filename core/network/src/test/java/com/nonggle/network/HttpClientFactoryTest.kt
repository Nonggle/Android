package com.nonggle.network

import com.example.common.result.AuthEvent
import com.example.common.result.AuthEventBus
import com.nonggle.auth.di.TokenManager
import com.nonggle.network.model.auth.TokenResponseDto
import com.nonggle.network.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HttpClientFactoryTest {

    // 의존성들을 Mock으로 생성
    private lateinit var tokenManager: TokenManager
    private lateinit var refreshTokenService: AuthService
    private lateinit var authEventBus: AuthEventBus

    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        // 각 테스트 전에 Mock 객체들을 초기화
        tokenManager = mockk(relaxed = true)
        refreshTokenService = mockk()
        authEventBus = mockk(relaxed = true)
    }

    @Test
    fun `만료된 accessToken으로 API 호출 시, 토큰 갱신 후 요청이 성공해야 한다`() = runTest {
        // --- GIVEN (준비) ---
        val mockEngine = MockEngine { request ->
            val authHeader = request.headers[HttpHeaders.Authorization]
            if (authHeader == "Bearer expired_access_token") {
                respond(
                    content = "{\"error\":\"token expired\"}",
                    status = HttpStatusCode.Unauthorized,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else if (authHeader == "Bearer new_access_token") {
                respond(
                    content = "{\"message\":\"Success\"}",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                respond("Forbidden", HttpStatusCode.Forbidden)
            }
        }

        coEvery { tokenManager.getAccessToken() } returns "expired_access_token"
        coEvery { tokenManager.getRefreshToken() } returns "valid_refresh_token"

        val newTokens = TokenResponseDto(userId = 1, accessToken = "new_access_token", refreshToken = "new_refresh_token")
        coEvery { refreshTokenService.refresh("valid_refresh_token") } returns newTokens

        val apiClient = createTestApiClient(mockEngine)

        // --- WHEN (실행) ---
        val response = apiClient.get("/protected/resource")

        // --- THEN (검증) ---
        assertEquals(HttpStatusCode.OK, response.status)
        coVerify(exactly = 1) { refreshTokenService.refresh("valid_refresh_token") }
        coVerify(exactly = 1) { tokenManager.saveTokens("new_access_token", "new_refresh_token") }
    }

    @Test
    fun `유효하지 않은 refreshToken으로 갱신 실패 시 SessionExpired 이벤트 방출`() = runTest {
        // --- GIVEN (준비) ---
        val mockEngine = MockEngine { request ->
            // 첫 요청에는 항상 401 에러를 반환하도록 설정
            respond(
                content = "{\"error\":\"token expired\"}",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        coEvery { tokenManager.getAccessToken() } returns "expired_access_token"
        coEvery { tokenManager.getRefreshToken() } returns "invalid_refresh_token"

        // RefreshTokenService가 refresh를 호출하면 예외를 발생시키도록 설정
        coEvery { refreshTokenService.refresh("invalid_refresh_token") } throws Exception("Invalid Refresh Token")

        val apiClient = createTestApiClient(mockEngine)

        // --- WHEN (실행) ---
        val response = apiClient.get("/protected/resource")

        // --- THEN (검증) ---
        // 재시도에 실패했으므로, 최종 응답은 원래의 401이어야 함
        assertEquals(HttpStatusCode.Unauthorized, response.status)

        // 토큰 갱신을 한 번 시도했는지 확인
        coVerify(exactly = 1) { refreshTokenService.refresh("invalid_refresh_token") }

        // 토큰이 삭제되었는지 확인
        coVerify(exactly = 1) { tokenManager.deleteToken() }

        // 가장 중요한 검증: SessionExpired 이벤트가 방출되었는지 확인
        coVerify(exactly = 1) { authEventBus.emit(AuthEvent.SessionExpired) }
    }

    /**
     * 테스트를 위해 Ktor 엔진을 교체할 수 있도록 만든 헬퍼 함수.
     * HttpClientFactory의 createApiClient 로직과 동일해야 합니다.
     */
    private fun createTestApiClient(engine: MockEngine): HttpClient {
        val refreshMutex = Mutex()
        return HttpClient(engine) {
            install(ContentNegotiation) { json(json) }
            install(Auth) {
                bearer {
                    loadTokens {
                        val access = tokenManager.getAccessToken()
                        val refresh = tokenManager.getRefreshToken()
                        if (access != null && refresh != null) BearerTokens(access, refresh) else null
                    }
                    refreshTokens {
                        refreshMutex.withLock {
                            val refreshToken = tokenManager.getRefreshToken() ?: return@withLock null
                            val result = runCatching { refreshTokenService.refresh(refreshToken) }
                            result.fold(
                                onSuccess = {
                                    tokenManager.saveTokens(it.accessToken, it.refreshToken)
                                    BearerTokens(it.accessToken, it.refreshToken)
                                },
                                onFailure = {
                                    tokenManager.deleteToken()
                                    authEventBus.emit(AuthEvent.SessionExpired)
                                    null
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}