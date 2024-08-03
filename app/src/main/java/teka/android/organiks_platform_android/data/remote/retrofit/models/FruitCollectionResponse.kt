package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity


@Serializable
data class FruitCollectionDto(
    val uuid: String,
    val quantity: String,
    val fruitTypeId: Int,
    val date: Long,  // Date as a timestamp
)

fun FruitCollectionDto.toFruitCollectionEntity(): FruitCollectionEntity {
    return FruitCollectionEntity(
        uuid = this.uuid,
        qty = this.quantity,
        fruitTypeId = this.fruitTypeId,
        date = this.date
    )
}