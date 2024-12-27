package teka.android.organiks_platform_android.presentation.module_farm_management.screens.my_farm.tabs.my_expenditure

import androidx.compose.runtime.Stable
import teka.android.organiks_platform_android.data.room.entities.ExpenditureEntity


@Stable
data class ExpenditureListUIState(
    var expenditureList: List<ExpenditureEntity> = emptyList(),
    var isFetchingExpenditures: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var farmSearchQuery: String = "",
    var errorMessage: String? = null,
    var connectivityStatus: String? = null,
    var selectedDateRange: Pair<Long?, Long?> = null to null,
    ){
    companion object {
        val default = ExpenditureListUIState()
    }
}