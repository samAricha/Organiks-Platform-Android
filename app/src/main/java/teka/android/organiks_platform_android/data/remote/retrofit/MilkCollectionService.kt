package teka.android.organiks_platform_android.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionRequest
import teka.android.organiks_platform_android.data.room_remote_sync.models.RemoteMilkCollectionResponse

interface MilkCollectionService {


    @POST("api/milkCollection/update")
    suspend fun updateEventVisitorList(@Body milkCollection: MilkCollectionRequest)


    @GET("collections/milk")
    suspend fun getMilkCollection():MilkCollectionResponse


    //Remember to adjust the response model class based on the actual JSON structure returned by your server's API.
    @POST("collections/milk")
    suspend fun createRemoteMilkCollection(@Body milkCollection: MilkCollectionRequest): RemoteMilkCollectionResponse

}
