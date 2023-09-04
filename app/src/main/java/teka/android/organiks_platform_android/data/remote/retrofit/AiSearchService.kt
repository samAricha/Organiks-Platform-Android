package teka.android.organiks_platform_android.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionRequest
import teka.android.organiks_platform_android.data.remote.retrofit.models.OpenAiPromptModel
import teka.android.organiks_platform_android.data.room_remote_sync.models.OpenAiSearchResponse
import teka.android.organiks_platform_android.data.room_remote_sync.models.RemoteEggCollectionResponse

interface AiSearchService {


    @POST("api")
    suspend fun getOpenAiResponse(@Body openAiPromptModel: OpenAiPromptModel): OpenAiSearchResponse

}
