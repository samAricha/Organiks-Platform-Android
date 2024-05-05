package teka.android.organiks_platform_android.modules.auth.core.data.dto


import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.modules.auth.core.data.dto.Geolocation

@Serializable
data class Address(
    val city: String,
    val geolocation: Geolocation,
    val number: Int,
    val street: String,
    val zipcode: String
)