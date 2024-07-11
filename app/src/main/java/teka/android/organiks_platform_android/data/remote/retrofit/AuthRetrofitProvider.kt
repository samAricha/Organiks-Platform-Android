package teka.android.organiks_platform_android.data.remote.retrofit


import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.CustomAuthService

object AuthRetrofitProvider {

    private fun provide(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }//to ignore unkown keys

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_URL)
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
            .build()

    fun createAuthService(): CustomAuthService {
        return provide().create(CustomAuthService::class.java)
    }
}