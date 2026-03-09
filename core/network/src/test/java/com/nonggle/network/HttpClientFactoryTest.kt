package com.nonggle.network

import com.example.common.result.AuthEvent
import com.example.common.result.AuthEventBus
import com.nonggle.auth.di.TokenManager
import com.nonggle.model.AppError
import com.nonggle.model.AppResult
import com.nonggle.network.model.auth.TokenResponseDto
import com.nonggle.network.service.AuthService
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class HttpClientFactoryTest {

    private lateinit var tokenManager: TokenManager
    private lateinit var authService: AuthService
    private lateinit var authEventBus: AuthEventBus

    @Before
    fun setup() {
        tokenManager = mockk(relaxed = true)
        authService = mockk()
        authEventBus = mockk(relaxed = true)
    }

    /**
     * accessToken 만료(401) → refresh 성공 → 새 토큰 저장, SessionExpired 미방출
     */
    @Test
    fun `accessToken 만료 후 refresh 성공시 새 토큰을 저장하고 SessionExpired를 방출하지 않는다`() = runTest {
        val newAccessToken = "new-access-token"
        val newRefreshToken = "new-refresh-token"

        var requestCount = 0
        val mockEngine = MockEngine { _ ->
            requestCount++
            if (requestCount == 1) {
                respond("", HttpStatusCode.Unauthorized)
            } else {
                respond(
                    content = """{"success":true,"data":null}""",
                    status = HttpStatusCode.OK,
                    headers = io.ktor.http.headersOf(
                        io.ktor.http.HttpHeaders.ContentType, "application/json"
                    )
                )
            }
        }

        coEvery { tokenManager.getAccessToken() } returns "expired-access-token"
        coEvery { tokenManager.getRefreshToken() } returns "valid-refresh-token"
        coEvery { authService.refresh(any()) } returns AppResult.Success(
            TokenResponseDto(
                userId = 1L,
                accessToken = newAccessToken,
                refreshToken = newRefreshToken,
            )
        )

        val client = HttpClientFactory.createApiClient(
            tokenManager = tokenManager,
            refreshTokenService = authService,
            authEventBus = authEventBus,
            engine = mockEngine,
        )

        client.get("/api/test")

        coVerify { tokenManager.saveTokens(newAccessToken, newRefreshToken) }
        coVerify(exactly = 0) { authEventBus.emit(any()) }
    }

    /**
     * accessToken 만료(401) → refreshToken null → SessionExpired 방출, 토큰 삭제 없음
     */
    @Test
    fun `refreshToken이 null이면 SessionExpired를 방출하고 deleteToken을 호출하지 않는다`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond("", HttpStatusCode.Unauthorized)
        }

        coEvery { tokenManager.getAccessToken() } returns "expired-access-token"
        coEvery { tokenManager.getRefreshToken() } returns null

        val client = HttpClientFactory.createApiClient(
            tokenManager = tokenManager,
            refreshTokenService = authService,
            authEventBus = authEventBus,
            engine = mockEngine,
        )

        client.get("/api/test")

        coVerify(exactly = 1) { authEventBus.emit(AuthEvent.SessionExpired) }
        coVerify(exactly = 0) { tokenManager.deleteToken() }
    }

    /**
     * accessToken 만료(401) → refresh API 실패 → 토큰 삭제 + SessionExpired 방출
     */
    @Test
    fun `refresh API 실패시 토큰을 삭제하고 SessionExpired를 방출한다`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond("", HttpStatusCode.Unauthorized)
        }

        coEvery { tokenManager.getAccessToken() } returns "expired-access-token"
        coEvery { tokenManager.getRefreshToken() } returns "invalid-refresh-token"
        coEvery { authService.refresh(any()) } returns AppResult.Error(
            AppError.Http(401, "Unauthorized")
        )

        val client = HttpClientFactory.createApiClient(
            tokenManager = tokenManager,
            refreshTokenService = authService,
            authEventBus = authEventBus,
            engine = mockEngine,
        )

        client.get("/api/test")

        coVerify(exactly = 1) { tokenManager.deleteToken() }
        coVerify(exactly = 1) { authEventBus.emit(AuthEvent.SessionExpired) }
    }
}
