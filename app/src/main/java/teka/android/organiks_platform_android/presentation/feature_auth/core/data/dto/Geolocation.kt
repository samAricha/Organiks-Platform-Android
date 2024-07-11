package teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Geolocation(
    val lat: String,
    val long: String
)