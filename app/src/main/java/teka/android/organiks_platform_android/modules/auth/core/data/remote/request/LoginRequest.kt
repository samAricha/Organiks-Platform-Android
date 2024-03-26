package teka.android.organiks_platform_android.modules.auth.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val username: String
)