package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.MilkCollection

@Serializable
data class MilkCollectionRequest(
    val collection_uuid: String,
    val quantity: String,
    val date: Long,
    val createdAt: Long
)

fun MilkCollection.toMilkCollectionRequest(): MilkCollectionRequest {
    return MilkCollectionRequest(
        collection_uuid = this.uuid,
        quantity = this.qty,
        date = this.date,
        createdAt = this.createdAt
    )
}

fun MilkCollectionRequest.toMilkCollection(): MilkCollection {
    return MilkCollection(
        uuid = this.collection_uuid,
        qty = this.quantity,
        date = this.date,
        createdAt = this.createdAt
    )
}
