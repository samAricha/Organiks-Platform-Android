package teka.android.organiks_platform_android.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(val progress: Double? = null, data: T? = null) : Resource<T>(data) // Add progress
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Idle<T> : Resource<T>()
}