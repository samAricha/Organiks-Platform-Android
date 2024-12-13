package teka.android.organiks_platform_android.presentation.feature_auth.core.data.remote.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginRequest(
    val password: String,
    val username: String
)