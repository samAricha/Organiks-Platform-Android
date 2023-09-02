package teka.android.organiks_platform_android.data.room_remote_sync.models

import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection

data class RemoteEggCollectionResponse(
    val success: Boolean,
    val message: String?,
    val data: EggCollection?
)

data class RemoteMilkCollectionResponse(
    val success: Boolean,
    val message: String?,
    val data: MilkCollection?
)