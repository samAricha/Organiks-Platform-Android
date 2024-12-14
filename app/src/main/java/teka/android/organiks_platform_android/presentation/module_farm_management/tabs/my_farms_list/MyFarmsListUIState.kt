package teka.android.organiks_platform_android.presentation.module_farm_management.tabs.my_farms_list

import androidx.compose.runtime.Stable
import teka.android.organiks_platform_android.data.room.entities.FarmEntity


@Stable
data class MyFarmsListUIState(
    var farmList: List<FarmEntity> = emptyList(),
    var isFetchingFarms: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var farmSearchQuery: String = "",
    var errorMessage: String? = null,
    var connectivityStatus: String? = null,
    var selectedDateRange: Pair<Long?, Long?> = null to null,
    ){
    companion object {
        val default = MyFarmsListUIState()
    }
}