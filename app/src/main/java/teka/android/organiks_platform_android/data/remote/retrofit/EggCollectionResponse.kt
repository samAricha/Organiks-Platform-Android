package teka.android.organiks_platform_android.data.remote.retrofit

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection
import java.util.Date

@Serializable
data class EggCollectionResponse(
    val page: Int? = null,
    val results: List<EggCollectionResult>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class EggCollectionResult(
    val qty: String,
    val cracked: String,
    val eggTypeId: Int,
    val dateTimestamp: Long,  // Date as a timestamp
) {
    val date: Date
        get() = Date(dateTimestamp)
}

fun EggCollectionResult.toEggCollection(): EggCollection {
    return EggCollection(
        qty = this.qty,
        cracked = this.cracked,
        eggTypeId = this.eggTypeId,
        date = this.date
    )
}