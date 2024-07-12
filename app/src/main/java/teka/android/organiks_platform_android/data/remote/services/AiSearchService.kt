package teka.android.organiks_platform_android.data.remote.services

import retrofit2.http.Body
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.remote.retrofit.models.OpenAiPromptModel
import teka.android.organiks_platform_android.data.room_remote_sync.models.OpenAiSearchResponse

interface AiSearchService {


    @POST("api")
    suspend fun getOpenAiResponse(@Body openAiPromptModel: OpenAiPromptModel): OpenAiSearchResponse

}
