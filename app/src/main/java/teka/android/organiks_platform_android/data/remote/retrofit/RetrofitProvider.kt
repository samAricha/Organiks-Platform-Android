package teka.android.organiks_platform_android.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitProvider {
//    private const val BASE_URL = "https://api.themoviedb.org"
    private const val BASE_URL = "https://e599-2c0f-fe38-2401-1688-b69d-ef46-ee54-c3aa.ngrok-free.app/"

    private fun provide(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }//to ignore unkown keys

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkhttpClient())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private fun provideOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor)
            .build()


    fun createEggCollectionService(): EggCollectionService {
        return provide().create(EggCollectionService::class.java)
    }

    fun createMilkCollectionService(): MilkCollectionService {
        return provide().create(MilkCollectionService::class.java)
    }

    fun createAuthService(): AuthService {
        return provide().create(AuthService::class.java)
    }

}