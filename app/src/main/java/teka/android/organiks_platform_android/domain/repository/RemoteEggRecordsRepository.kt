package teka.android.organiks_platform_android.domain.repository

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
                val response: ApiResponseHandler<List<EggCollectionResult>> = eggCollectionService.getAllEggCollections()
                Timber.tag(">>>PROPERTIES LIST").d(response.toString())
                response.data.let {
                    if (it != null) {
                        emit(it)
                    }
                }
            } catch (e: Exception) {
                Timber.tag(">>>PROPERTIES LIST ERROR").e(e)
                // Handle exceptions here (e.g., network issues)
                emit(emptyList()) // Emit an empty list or handle error accordingly
            }
        }
    }


}
