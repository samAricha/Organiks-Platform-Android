package teka.android.organiks_platform_android.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room_remote_sync.models.CreateEggCollectionResponse

interface ApiService {
    companion object{
        var apiService:ApiService ?= null
        fun getInstance(): ApiService{

            if (apiService == null){
               val retrofit = Retrofit.Builder()
                    .baseUrl("remote url")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }
    }

    @GET("api/eggCollections/get")
    suspend fun getEggCollections():List<EggCollection>


    //Remember to adjust the response model class based on the actual JSON structure returned by your server's API.
    @POST("api/eggCollections/save")
    suspend fun createEggCollection(@Body eggCollection: EggCollection): CreateEggCollectionResponse



}