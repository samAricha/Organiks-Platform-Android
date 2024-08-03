package teka.android.organiks_platform_android.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionRequest
import teka.android.organiks_platform_android.data.room_remote_sync.models.SaveCollectionResponse

interface FruitCollectionService {


    @POST("eventVisitors/update")
    suspend fun updateFruitCollection(@Body fruitCollection: FruitCollectionRequest)


    @GET("collections/fruits/get")
    suspend fun getAllFruitCollections(): ApiResponseHandler<List<FruitCollectionDto>>


    @POST("collections/fruits/store")
    suspend fun createRemoteFruitCollection(@Body fruitCollection: FruitCollectionRequest): SaveCollectionResponse

}
