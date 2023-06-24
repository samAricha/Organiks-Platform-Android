package teka.android.organiks_platform_android.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import teka.android.organiks_platform_android.data.room.models.EggCollection

interface ApiService {

    @GET("eggCollection.json")
    suspend fun getEggCollections():List<EggCollection>

    companion object{
        var apiService:ApiService ?= null
        fun getInstance(): ApiService{

            if (apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl("remote url")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}