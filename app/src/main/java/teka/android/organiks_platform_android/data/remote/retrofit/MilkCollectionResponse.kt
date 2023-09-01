package teka.android.organiks_platform_android.data.remote.retrofit

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import java.util.Date

@Serializable
data class MilkCollectionResponse(
    val page: Int? = null,
    val results: List<MilkCollectionResult>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class MilkCollectionResult(
    val uuid: String,
    val qty: String,
    val date: Long,
)

fun MilkCollectionResult.toMilkCollection(): MilkCollection {
    return MilkCollection(
        uuid = this.uuid,
        qty = this.qty,
        date = this.date,
    )
}