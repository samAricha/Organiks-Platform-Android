package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection


@Serializable
data class EggCollectionResult(
    @SerialName("collection_uuid")
    val uuid: String,
    val quantity: String,
    val cracked: String,
    @SerialName("egg_type_id")
    val eggTypeId: Int,
    val date: Long,
)

fun EggCollectionResult.toEggCollection(): EggCollection {
    return EggCollection(
        uuid = this.uuid,
        qty = this.quantity,
        cracked = this.cracked,
        eggTypeId = this.eggTypeId,
        date = this.date
    )
}