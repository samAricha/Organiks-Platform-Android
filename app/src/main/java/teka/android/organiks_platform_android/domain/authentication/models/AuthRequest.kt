package teka.android.organiks_platform_android.domain.authentication.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
    )

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class PersonInfoRequest(
    val username: String,
    val password: String
)