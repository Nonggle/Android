package com.nonggle.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun provideHttpClient(
    engine: HttpClientEngine,
    //tokenManager: TokenManager
): HttpClient {
    return HttpClient(engine) {
        // 1. JSON 직렬화/역직렬화 설정
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // API 응답에 모르는 필드가 있어도 무시
            })
        }

        // 2. 로깅 설정
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    // 안드로이드 Logcat에 'KtorLogger' 태그로 출력
                    //Log.v("KtorLogger", message)
                }
            }
            level = LogLevel.ALL // 개발 중에는 모든 요청/응답 내용을 확인
        }

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Auth) {
            bearer {
                // 저장된 토큰 불러오기
                loadTokens {
                    /// FIXME: 토큰 가져오는 로직 구현
                    // tokemManager.getToken()
                    BearerTokens("", "")
                }
                // 401응답 올때 호출, 기존 accessToken 만료시 새 토큰 발급 로직 정의
                refreshTokens {
                    // client.post("/auth/refresh") { ... }
                    // 발급받은 새로운 토큰 저장 로직
                    BearerTokens("", "")
                }

                sendWithoutRequest { request ->
//                    tokenManager.hasAccessToken() &&
//                            request.url.encodedPath !in setOf("/auth/login", "/auth/signup", "/auth/refresh")
                }
            }
        }

        // 4. 기본 요청 설정
        defaultRequest {
            // local.properties에서 정의한 baseUrl을 모든 요청의 기본 URL로 사용합니다.
            // BuildConfig는 Gradle에 의해 자동으로 생성됩니다.
            url(BuildConfig.BASE_URL)

            // 모든 요청에 기본적으로 포함될 헤더
            header("App-Version", "1.0.0") // 예시 헤더
            contentType(ContentType.Application.Json)
        }

    }
}