package teka.android.organiks_platform_android.domain.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.services.EggCollectionService
import timber.log.Timber

class RemoteEggRecordsRepository(
    private val eggCollectionService: EggCollectionService,
) {

    suspend fun getAllEggCollections(): Flow<List<EggCollectionResult>> {
        return flow {
            try {
                val response: ApiResponseHandler<List<EggCollectionResult>> = retryWithDelay(
                    retries = 3,
                    delayMillis = 2000
                ) {
                    eggCollectionService.getAllEggCollections()
                }
                Timber.tag(">>>EGG LIST").d(response.toString())
                response.data?.let {
                    emit(it)
                } ?: emit(emptyList())
            } catch (e: Exception) {
                Timber.tag(">>>EGG LIST ERROR").e(e)
                emit(emptyList())
            }
        }
    }



    private suspend fun <T> retryWithDelay(
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


}
