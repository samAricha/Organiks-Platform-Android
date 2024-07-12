package teka.android.organiks_platform_android.data.remote.retrofit

import okhttp3.Interceptor

object HeaderInterceptor : Interceptor {
    private var authToken: String? = null

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val request = chain.request()
        val modifiedRequest = request.newBuilder()
            .header("Accept", "application/vnd.api+json")
            .addHeader("Authorization", "Bearer ")
            .build()

        return chain.proceed(modifiedRequest)
    }
}
