package teka.android.organiks_platform_android.data.remote.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection

@Serializable
data class EggCollectionResponse(
    val page: Int? = null,
    val results: List<EggCollectionResult>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class EggCollectionResult(
    val uuid: String,
    val quantity: String,
    val cracked: String,
    @SerialName("egg_type_id")
    val eggTypeId: Int,
    val date: Long,  // Date as a timestamp
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