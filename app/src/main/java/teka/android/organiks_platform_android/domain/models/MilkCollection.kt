package teka.android.organiks_platform_android.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MilkCollectionUpstream(
    val uuid: String,
    val qty:String,
    val date: Long,
    val collectionDate: Long
)