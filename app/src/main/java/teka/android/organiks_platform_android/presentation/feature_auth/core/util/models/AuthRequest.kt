package teka.android.organiks_platform_android.presentation.feature_auth.core.util.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RegisterRequestBody(
    val name: String? = null,
    val phone: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginRequestBody(
    val username: String,
    val password: String
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PersonInfoRequest(
    val username: String,
    val password: String
)