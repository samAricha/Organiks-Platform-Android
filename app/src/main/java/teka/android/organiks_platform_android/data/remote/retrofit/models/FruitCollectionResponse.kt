package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity

@Serializable
data class FruitCollectionResponse(
    val page: Int? = null,
    val results: List<EggCollectionResult>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class FruitCollectionDto(
    val uuid: String,
    val qty: String,
    val fruitTypeId: Int,
    val date: Long,  // Date as a timestamp
)

fun FruitCollectionDto.toFruitCollectionEntity(): FruitCollectionEntity {
    return FruitCollectionEntity(
        uuid = this.uuid,
        qty = this.qty,
        fruitTypeId = this.fruitTypeId,
        date = this.date
    )
}