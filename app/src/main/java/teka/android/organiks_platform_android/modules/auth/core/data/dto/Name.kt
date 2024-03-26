package teka.android.organiks_platform_android.modules.auth.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val firstname: String,
    val lastname: String
)