package teka.android.organiks_platform_android.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionRequest
import teka.android.organiks_platform_android.data.room_remote_sync.models.RemoteEggCollectionResponse

interface EggCollectionService {
    @POST("api/eventVisitors/update")
    suspend fun updateEggCollection(@Body eggCollection: EggCollectionRequest)


    @GET("collections/egg")
    suspend fun getEggCollections():EggCollectionResponse


    @POST("api/collections/egg/store")
    suspend fun createRemoteEggCollection(@Body eggCollection: EggCollectionRequest): RemoteEggCollectionResponse

}
