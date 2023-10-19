package teka.android.organiks_platform_android.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitProvider {
    private const val TEST_URL = "https://a3fb-105-57-17-70.ngrok-free.app"
//    private const val TEST_URL = "https://c5bf-2c0f-fe38-2407-af33-288b-ff34-18af-45d1.ngrok-free.app"
    private const val BASE_URL = "http://191.101.0.246:8081"

    private fun provide(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }//to ignore unkown keys

        return Retrofit.Builder()
            .baseUrl(TEST_URL)
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

    fun createFruitCollectionService(): FruitCollectionService {
        return provide().create(FruitCollectionService::class.java)
    }

    fun createAuthService(): AuthService {
        return provide().create(AuthService::class.java)
    }

}