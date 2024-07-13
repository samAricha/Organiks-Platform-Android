package teka.android.organiks_platform_android.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.data.remote.services.MilkCollectionService
import timber.log.Timber

class RemoteMilkRecordsRepository(
    private val milkCollectionService: MilkCollectionService,
) {

    suspend fun getAllMilkCollections(): Flow<List<MilkCollectionResult>> {
        return flow {
            try {
                val response: ApiResponseHandler<List<MilkCollectionResult>> = milkCollectionService.getAllMilkCollection()
                Timber.tag(">>>milk LIST").d(response.toString())
                response.data.let {
                    if (it != null) {
                        emit(it)
                    }
                }
            } catch (e: Exception) {
                Timber.tag(">>>MILK LIST ERROR").e(e)
                // Handle exceptions here (e.g., network issues)
                emit(emptyList())
            }
        }
    }


}
