package teka.android.organiks_platform_android.domain.repository.utils

import kotlinx.coroutines.delay

suspend fun <T> retryApiRequestWithDelay(
        retries: Int,
        delayMillis: Long,
        block: suspend () -> T
    ): T {
        var attempt = 0
        var lastException: Exception? = null
        while (attempt < retries) {
            try {
                return block()
            } catch (e: Exception) {
                lastException = e
                attempt++
                if (attempt < retries) {
                    delay(delayMillis)
                }
            }
        }
        throw lastException ?: RuntimeException("Unknown error")
    }