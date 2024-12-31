package teka.android.organiks_platform_android.util.helpers

import kotlinx.coroutines.delay
import java.net.UnknownHostException

suspend fun retryWithDelay(
        maxRetries: Int,
        initialDelay: Long = 1000L,
        delayFactor: Double = 2.0,
        block: suspend () -> Unit
    ) {
        var currentDelay = initialDelay
        repeat(maxRetries) { attempt ->
            try {
                block()
                return
            } catch (e: UnknownHostException) {
                if (attempt == maxRetries - 1) throw e
                delay(currentDelay)
                currentDelay = (currentDelay * delayFactor).toLong()
            }
        }
    }