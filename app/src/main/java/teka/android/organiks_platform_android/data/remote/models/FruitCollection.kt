package teka.android.organiks_platform_android.data.remote.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FruitCollectionRequest(
    val collection_uuid: String,
    val quantity: String,
    val fruit_type_id: String,
    val date: Long,
    val time: String,
    val collection_date: Long,
)

fun FruitCollectionEntity.toFruitCollectionRequest(): FruitCollectionRequest {
    return FruitCollectionRequest(
        collection_uuid = this.uuid,
        quantity = this.qty,
        date = this.date,
        time = this.time,
        fruit_type_id = this.fruitTypeId.toString(),
        collection_date = this.date,
    )
}

fun FruitCollectionRequest.toFruitCollection(): FruitCollectionEntity {
    return FruitCollectionEntity(
        uuid = this.collection_uuid,
        qty = this.quantity,
        date = this.collection_date,
        time = this.time,
        fruitTypeId = this.fruit_type_id,
        createdAt = this.date

    )
}
