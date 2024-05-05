package teka.android.organiks_platform_android.modules.auth.core.util.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestBody(
    val name: String? = null,
    val phone: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

@Serializable
data class LoginRequestBody(
    val username: String,
    val password: String
)

@Serializable
data class PersonInfoRequest(
    val username: String,
    val password: String
)