package teka.android.organiks_platform_android.data.remote.retrofit

import okhttp3.Interceptor

object HeaderInterceptor : Interceptor {
    var authToken: String? = null

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val request = chain.request()
        val modifiedRequest = authToken?.let { token ->
            request.newBuilder()
                .header("Accept", "application/vnd.api+json")
                .apply {
                    // Add Authorization header if authToken is not null
                    authToken?.let { token ->
                        header("Authorization", "Bearer $token")
                    }
                }
                .build()
        } ?: request // If authToken is null, proceed with the original request
        return chain.proceed(modifiedRequest)
    }
}
