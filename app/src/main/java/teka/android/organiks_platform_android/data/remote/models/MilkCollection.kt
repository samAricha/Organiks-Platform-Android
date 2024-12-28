package teka.android.organiks_platform_android.data.remote.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.entities.MilkCollection

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MilkCollectionRequest(
    val collection_uuid: String,
    val quantity: String,
    val date: Long,
    val collection_date: Long,
)

fun MilkCollection.toMilkCollectionRequest(): MilkCollectionRequest {
    return MilkCollectionRequest(
        collection_uuid = this.uuid,
        quantity = this.qty,
        date = this.createdAt,
        collection_date = this.date,
    )
}

fun MilkCollectionRequest.toMilkCollection(): MilkCollection {
    return MilkCollection(
        uuid = this.collection_uuid,
        qty = this.quantity,
        date = this.collection_date,
        createdAt = this.date

    )
}
