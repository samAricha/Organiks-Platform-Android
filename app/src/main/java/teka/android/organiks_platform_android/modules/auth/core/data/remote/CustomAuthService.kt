package teka.android.organiks_platform_android.modules.auth.core.data.remote


import teka.android.organiks_platform_android.modules.auth.core.util.models.LoginRequestBody
import teka.android.organiks_platform_android.modules.auth.core.util.models.PersonInfoRequest
import teka.android.organiks_platform_android.modules.auth.core.util.models.LoggedInUser
import retrofit2.http.Body
import retrofit2.http.POST
import teka.android.organiks_platform_android.modules.auth.core.util.models.LoginResponseData
import teka.android.organiks_platform_android.modules.auth.core.util.models.RegisterRequestBody
import teka.android.organiks_platform_android.modules.auth.core.util.models.RegisterResponseData
import teka.android.organiks_platform_android.util.data.ApiResponseHandler

interface CustomAuthService {

    @POST("api/auth/register")
    suspend fun registration(
        @Body registerRequest: RegisterRequestBody
    ): ApiResponseHandler<RegisterResponseData>

    @POST("/api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequestBody
    ): ApiResponseHandler<LoginResponseData>





//    @POST("api/auth/register")
//    suspend fun registration(
//        @Body registerRequest: RegisterRequestBody
//    ): Response<AuthResponse>
//    @POST("/api/auth/login")
//    suspend fun login(
//        @Body loginRequest: LoginRequestBody
//    ): Response<LoginAuthResponse>

    @POST("/api/auth/me")
    suspend fun getMeInfo(
        @Body personInfoRequest: PersonInfoRequest
    ): LoggedInUser
}