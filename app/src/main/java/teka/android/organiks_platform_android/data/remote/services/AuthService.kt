package teka.android.organiks_platform_android.data.remote.services

import retrofit2.http.Body
import retrofit2.http.POST
import teka.android.organiks_platform_android.domain.authentication.models.PersonInfoRequest
import teka.android.organiks_platform_android.domain.authentication.models.User
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.LoginRequestBody
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.LoginResponseData
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.RegisterRequestBody
import teka.android.organiks_platform_android.presentation.feature_auth.core.util.models.RegisterResponseData
import teka.android.organiks_platform_android.util.data.ApiResponseHandler

interface AuthService {

    @POST("api/auth/register")
    suspend fun registration(
        @Body registerRequest: RegisterRequestBody
    ): ApiResponseHandler<RegisterResponseData>

    @POST("/api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequestBody
    ): ApiResponseHandler<LoginResponseData>

    @POST("/api/auth/me")
    suspend fun getMeInfo(
        @Body personInfoRequest: PersonInfoRequest
    ): User
}