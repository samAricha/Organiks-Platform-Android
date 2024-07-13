package teka.android.organiks_platform_android.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResponse
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.room_remote_sync.models.RemoteEggCollectionResponse

interface EggCollectionService {
    @POST("eventVisitors/update")
    suspend fun updateEggCollection(@Body eggCollection: EggCollectionRequest)


    @GET("collections/egg/get")
    suspend fun getAllEggCollections(): ApiResponseHandler<List<EggCollectionResult>>


    @POST("collections/egg/store")
    suspend fun createRemoteEggCollection(@Body eggCollection: EggCollectionRequest): RemoteEggCollectionResponse

}
