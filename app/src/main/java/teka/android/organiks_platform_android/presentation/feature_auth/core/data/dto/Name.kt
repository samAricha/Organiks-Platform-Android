package teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Name(
    val firstname: String,
    val lastname: String
)