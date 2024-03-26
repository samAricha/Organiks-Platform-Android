package teka.android.organiks_platform_android.modules.auth.core.util.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseData(
    val user: RegisteredUser,
)

@Serializable
data class LoginResponseData(
    val user: LoggedInUser,
    val access_token: String
)

@Serializable
data class LoggedInUser(
    val id: Int,
    val name: String,
    val email: String?,
    val phone: String,
    val created_at: String,
    val updated_at: String,
    val roles: List<UserRoleDto>
)

@Serializable
data class RegisteredUser(
    val name: String,
    val email: String?,
    val phone: String?,
    val created_at: String,
    val updated_at: String,
)





