package teka.android.organiks_platform_android.presentation.feature_auth.core.util.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RegisterResponseData(
    val user: RegisteredUser,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginResponseData(
    val user: LoggedInUser,
    val access_token: String
)

@SuppressLint("UnsafeOptInUsageError")
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

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RegisteredUser(
    val name: String,
    val email: String?,
    val phone: String?,
    val created_at: String,
    val updated_at: String,
)





