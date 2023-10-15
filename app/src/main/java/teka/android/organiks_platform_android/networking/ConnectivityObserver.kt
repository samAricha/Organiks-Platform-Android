package teka.android.organiks_platform_android.networking

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status{
        Available, Unavailable, Losing, Lost
    }


}