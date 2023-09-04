package teka.android.organiks_platform_android.data.room_remote_sync.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection

@Serializable
data class RemoteEggCollectionResponse(
    val success: Boolean,
    val message: String?,
)

@Serializable
data class RemoteMilkCollectionResponse(
    val success: Boolean,
    val message: String?,
)

@Serializable
data class OpenAiSearchResponse(
    val result: String?,
)