package teka.android.organiks_platform_android.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.room.models.MilkCollection

interface MilkCollectionService {


    @POST("api/milkCollection/update")
    suspend fun updateEventVisitorList(@Body milkCollection: MilkCollection)


    @GET("api/milkCollection/get")
    suspend fun getMilkCollection():MilkCollectionResponse


    //Remember to adjust the response model class based on the actual JSON structure returned by your server's API.
    @POST("api/eggCollections/save")
    suspend fun createRemoteEggCollection(@Body milkCollection: MilkCollection)

}
