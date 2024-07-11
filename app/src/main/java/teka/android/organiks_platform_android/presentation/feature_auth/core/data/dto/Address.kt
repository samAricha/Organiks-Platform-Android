package teka.android.organiks_platform_android.presentation.feature_auth.core.data.dto


import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val city: String,
    val geolocation: Geolocation,
    val number: Int,
    val street: String,
    val zipcode: String
)