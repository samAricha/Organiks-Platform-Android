package teka.android.organiks_platform_android.modules.auth.core.data.remote.response


import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)