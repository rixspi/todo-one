package life.catchyour.base.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

private const val TIME_OUT = 60000

@ExperimentalSerializationApi
fun httpClientAndroid(
    refreshTokenUrl: String,
    getTokens: () -> BearerTokens?,
    saveTokens: (BearerTokens) -> Unit,
    logout: () -> Unit
) = HttpClient(Android) {


    expectSuccess = true

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            explicitNulls = false
        })

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }

    install(HttpCache)

    class EventoLogger : Logger {
        override fun log(message: String) {
            logUnlimited("HTTP", message)
        }

        fun logUnlimited(tag: String, string: String) {
            val maxLogSize = 1000
            string.chunked(maxLogSize).forEach { Log.d(tag, it) }
        }
    }

    // For Logging
    install(Logging) {
        logger = EventoLogger()
        level = LogLevel.ALL
    }


    install(Auth) {
        bearer {
            sendWithoutRequest { true }

            loadTokens {
                getTokens()
            }

//            refreshTokens {
//                val refreshToken = getTokens() ?: return@refreshTokens null
//
//                return@refreshTokens try {
//
//                    val getNewTokens = client.post {
//                        sendWithoutRequest { true }
//                        markAsRefreshTokenRequest()
//                        url(refreshTokenUrl)
//                        setBody(
//                            RefreshTokenRequestBody(
//                                refreshToken = refreshToken.refreshToken
//                            )
//                        )
//                    }.body<LoginResponse>()
//
//                    saveTokens(
//                        BearerTokens(
//                            accessToken = getNewTokens.accessToken,
//                            refreshToken = getNewTokens.refreshToken
//                        )
//                    )
//
//                    BearerTokens(
//                        accessToken = getNewTokens.accessToken,
//                        refreshToken = getNewTokens.refreshToken
//                    )
//                } catch (e: Exception) {
//                    Log.d("Error", e.message.toString())
//                    logout()
//                    null
//                }
//            }
        }
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}