package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.SerialName
import teka.android.organiks_platform_android.data.room.entities.EggCollection


data class EggCollectionResult(
    @SerialName("collection_uuid")
    val uuid: String = "",
    val quantity: String = "",
    val cracked: String = "",
    @SerialName("egg_type_id")
    val eggTypeId: Int = 0,
    @SerialName("collection_date")
    var date: Long = 0L
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