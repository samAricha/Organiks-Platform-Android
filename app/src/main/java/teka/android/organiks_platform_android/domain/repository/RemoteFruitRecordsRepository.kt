package teka.android.organiks_platform_android.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.services.FruitCollectionService
import timber.log.Timber

class RemoteFruitRecordsRepository(
    private val fruitCollectionService: FruitCollectionService,
) {

    suspend fun getAllFruitCollections(): Flow<List<FruitCollectionDto>> {
        return flow {
            try {
                val response: ApiResponseHandler<List<FruitCollectionDto>> = fruitCollectionService.getAllFruitCollections()
                Timber.tag(">>>FRUITS LIST").d(response.toString())
                response.data.let {
                    if (it != null) {
                        emit(it)
                    }
                }
            } catch (e: Exception) {
                Timber.tag(">>>FRUITS LIST ERROR").e(e)
                // Handle exceptions here (e.g., network issues)
                emit(emptyList()) // Emit an empty list or handle error accordingly
            }
        }
    }


}
