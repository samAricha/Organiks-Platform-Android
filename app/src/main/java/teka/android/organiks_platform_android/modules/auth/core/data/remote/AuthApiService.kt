package teka.android.organiks_platform_android.modules.auth.core.data.remote

import teka.android.organiks_platform_android.modules.auth.core.data.dto.UserResponseDto
import teka.android.organiks_platform_android.modules.auth.core.data.remote.request.LoginRequest
import teka.android.organiks_platform_android.modules.auth.core.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponseDto>
}