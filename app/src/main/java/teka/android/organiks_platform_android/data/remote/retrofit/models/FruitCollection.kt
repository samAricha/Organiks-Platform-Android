package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity

@Serializable
data class FruitCollectionRequest(
    val collection_uuid: String,
    val quantity: String,
    val fruitTypeId: Int,
    val date: Long,
    val collection_date: Long,
)

fun FruitCollectionEntity.toFruitCollectionRequest(): FruitCollectionRequest {
    return FruitCollectionRequest(
        collection_uuid = this.uuid,
        quantity = this.qty,
        date = this.createdAt,
        fruitTypeId = this.fruitTypeId,
        collection_date = this.date,
    )
}

fun FruitCollectionRequest.toFruitCollection(): FruitCollectionEntity {
    return FruitCollectionEntity(
        uuid = this.collection_uuid,
        qty = this.quantity,
        date = this.collection_date,
        fruitTypeId = this.fruitTypeId,
        createdAt = this.date

    )
}
