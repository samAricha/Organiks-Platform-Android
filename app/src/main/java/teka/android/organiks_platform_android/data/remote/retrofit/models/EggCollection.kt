package teka.android.organiks_platform_android.data.remote.retrofit.models

import teka.android.organiks_platform_android.data.room.models.EggCollection

data class EggCollectionRequest(
    val collection_uuid: String,
    val quantity: String,
    val cracked: String,
    val egg_type_id: Int,
    val date: Long,
    val createdAt: Long
)

fun EggCollection.toEggCollectionRequest(): EggCollectionRequest {
    return EggCollectionRequest(
        collection_uuid = this.uuid,
        quantity = this.qty,
        cracked = this.cracked,
        egg_type_id = this.eggTypeId,
        date = this.date,
        createdAt = this.createdAt
    )
}

fun EggCollectionRequest.toEggCollection(): EggCollection {
    return EggCollection(
        uuid = this.collection_uuid,
        qty = this.quantity,
        cracked = this.cracked,
        eggTypeId = this.egg_type_id,
        date = this.date,
        createdAt = this.createdAt
    )
}
