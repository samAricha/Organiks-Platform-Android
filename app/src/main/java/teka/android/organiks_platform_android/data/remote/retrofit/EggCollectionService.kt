package teka.android.organiks_platform_android.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room_remote_sync.models.CreateEggCollectionResponse

interface EggCollectionService {


    @POST("api/eventVisitors/update")
    suspend fun updateEggCollection(@Body eggCollection: EggCollection)


    @GET("api/eggCollections/get")
    suspend fun getEggCollections():EggCollectionResponse


    //Remember to adjust the response model class based on the actual JSON structure returned by your server's API.
    @POST("api/eggCollections/save")
    suspend fun createRemoteEggCollection(@Body eggCollection: EggCollection): CreateEggCollectionResponse

}
