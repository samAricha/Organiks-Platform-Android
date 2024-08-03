package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.MilkCollection


@Serializable
data class MilkCollectionResult(
    val uuid: String,
    val quantity: String,
    val date: Long,
)

fun MilkCollectionResult.toMilkCollection(): MilkCollection {
    return MilkCollection(
        uuid = this.uuid,
        qty = this.quantity,
        date = this.date,
    )
}