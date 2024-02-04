package teka.android.organiks_platform_android.domain.authentication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val isSuccessful: Boolean,
    val message: String?,
    val data: AuthData
)

@Serializable
data class AuthData(
    val user: User,
    @SerialName("access_token")
    val accessToken: String
)






@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String?,
    val phone: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)