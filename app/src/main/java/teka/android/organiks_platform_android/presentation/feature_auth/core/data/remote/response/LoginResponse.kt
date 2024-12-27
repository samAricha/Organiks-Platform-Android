package teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.response


import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginResponse(
    val token: String
)