package teka.android.organiks_platform_android.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.dtos.ApiResponseHandler
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResponse
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.data.room_remote_sync.models.RemoteMilkCollectionResponse

interface MilkCollectionService {


    @POST("milkCollection/update")
    suspend fun updateEventVisitorList(@Body milkCollection: MilkCollectionRequest)


    @GET("collections/milk/get")
    suspend fun getAllMilkCollection(): ApiResponseHandler<List<MilkCollectionResult>>


    //Remember to adjust the response model class based on the actual JSON structure returned by your server's API.
    @POST("collections/milk/store")
    suspend fun createRemoteMilkCollection(@Body milkCollection: MilkCollectionRequest): RemoteMilkCollectionResponse

}
