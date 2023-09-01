package teka.android.organiks_platform_android.domain.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class EggCollectionUpstream(
    val qty: String,
    val cracked: String,
    val eggTypeId: Int,
    val date: Long,
    val collectionDate: Long
)