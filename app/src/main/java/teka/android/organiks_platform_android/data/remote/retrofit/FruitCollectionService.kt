package teka.android.organiks_platform_android.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionRequest
import teka.android.organiks_platform_android.data.room_remote_sync.models.RemoteEggCollectionResponse

interface FruitCollectionService {


    @POST("api/eventVisitors/update")
    suspend fun updateFruitCollection(@Body fruitCollection: FruitCollectionRequest)


    @GET("collections/egg")
    suspend fun getFruitCollections():EggCollectionResponse


    @POST("api/collections/egg/store")
    suspend fun createRemoteFruitCollection(@Body fruitCollection: FruitCollectionRequest): RemoteEggCollectionResponse

}
