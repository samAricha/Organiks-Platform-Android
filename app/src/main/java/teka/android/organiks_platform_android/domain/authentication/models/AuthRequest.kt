package teka.android.organiks_platform_android.domain.authentication.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RegisterRequest(
    val name: String? = null,
    val phone: String,
    val email: String,
    val password: String,
    val password_confirmation: String
    )

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PersonInfoRequest(
    val username: String,
    val password: String
)