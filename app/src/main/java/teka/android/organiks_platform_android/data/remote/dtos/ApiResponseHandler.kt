package teka.android.organiks_platform_android.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseHandler<T>(
    val isSuccessful: Boolean,
    val status: String?,
    val message: String?,
    val data: T?
)


