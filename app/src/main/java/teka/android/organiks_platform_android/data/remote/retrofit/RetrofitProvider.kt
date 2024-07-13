package teka.android.organiks_platform_android.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.data.remote.services.AuthService
import teka.android.organiks_platform_android.data.remote.services.EggCollectionService
import teka.android.organiks_platform_android.data.remote.services.FruitCollectionService
import teka.android.organiks_platform_android.data.remote.services.MilkCollectionService
import java.util.concurrent.TimeUnit

object RetrofitProvider {
    private const val TEST_URL = "https://a3fb-105-57-17-70.ngrok-free.app"
    private const val BASE_URL = BuildConfig.BACKEND_URL

    private fun provide(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


    private fun provideAuthenticatedRetrofit(token: String): Retrofit {
        val json = Json { ignoreUnknownKeys = true }//to ignore unkown keys

        return Retrofit.Builder()
            .baseUrl(TEST_URL)
            .client(provideAuthenticatedOkhttpClient(token))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    private fun provideAuthenticatedOkhttpClient(token: String): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/vnd.api+json")
                    .addHeader("Authorization", "Bearer $token")
                chain.proceed(request.build())
            }
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build()




    fun createEggCollectionService(): EggCollectionService {
        return provide().create(EggCollectionService::class.java)
    }

    fun createMilkCollectionService(): MilkCollectionService {
        return provide().create(MilkCollectionService::class.java)
    }

    fun createFruitCollectionService(): FruitCollectionService {
        return provide().create(FruitCollectionService::class.java)
    }

    fun createAuthService(): AuthService {
        return provide().create(AuthService::class.java)
    }

}