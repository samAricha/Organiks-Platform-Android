package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection

@Serializable
data class EggCollectionRequest(
    val collection_uuid: String,
    val quantity: String,
    val cracked: String,
    val egg_type_id: String,
    val date: Long,
    val collection_date: Long
)

fun EggCollection.toEggCollectionRequest(): EggCollectionRequest {
    return EggCollectionRequest(
        collection_uuid = this.uuid,
        quantity = this.qty,
        cracked = this.cracked,
        egg_type_id = this.eggTypeId.toString(),
        date = this.date,
        collection_date = this.createdAt
    )
}

fun EggCollectionRequest.toEggCollection(): EggCollection {
    return EggCollection(
        uuid = this.collection_uuid,
        qty = this.quantity,
        cracked = this.cracked,
        eggTypeId = this.egg_type_id.toInt(),
        date = this.date,
        createdAt = this.collection_date
    )
}
