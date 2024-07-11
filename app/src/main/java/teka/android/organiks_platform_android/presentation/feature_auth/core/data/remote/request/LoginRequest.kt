package teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val username: String
)