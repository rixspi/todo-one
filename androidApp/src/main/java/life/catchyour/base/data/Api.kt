package life.catchyour.base.data

import io.ktor.client.request.headers
import io.ktor.http.HttpMessageBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import life.catchyour.base.app.Configuration
import life.catchyour.base.app.useCase.GetEnvUrl
import life.catchyour.dev.core.domain.catch


class Api constructor(
    getEnvUrl: GetEnvUrl,
    private val applicationScope: CoroutineScope
) {
    init {
        getEnvUrl()
            .distinctUntilChanged()
            .onEach {
                println("ENV CHANGED: $it")
                USE_DEV_API = it
            }
            .launchIn(applicationScope)
    }


    private suspend fun HttpMessageBuilder.appHeaders() {

        headers {

        }
    }

    companion object {
        private var USE_DEV_API = false
        val BASE_URL
            get() = if (USE_DEV_API) Configuration.DEV_API_URL else Configuration.PROD_API_URL
//            get() = Configuration.DEV_API_URL
//            get() = Configuration.PROD_API_URL
    }
}


suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return Result.catch { apiCall() }
}