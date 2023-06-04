package teka.android.organiks_platform_android.presentation.records.production.productionRecording

import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.ui.Category
import java.util.*


data class ProductionRecordingState(
    val eggTypesList: List<EggType> = emptyList(),
    val eggTypeName: String = "",
    val eggCollectionQty: String = "",

    val date: Date = Date(),
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
    val category: Category = Category()
)