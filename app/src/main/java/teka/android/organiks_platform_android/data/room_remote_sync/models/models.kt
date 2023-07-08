package teka.android.organiks_platform_android.data.room_remote_sync.models

import teka.android.organiks_platform_android.data.room.models.EggCollection

data class CreateEggCollectionResponse(
    val success: Boolean,
    val message: String?,
    val data: EggCollection?
)