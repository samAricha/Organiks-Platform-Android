package teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto.UserResponseDto
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.request.LoginRequest
import teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.response.LoginResponse

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponseDto>
}