package teka.android.organiks_platform_android.presentation.module_fruits.list_screen

import androidx.compose.runtime.Stable
import teka.android.organiks_platform_android.data.room.models.CustomerEntity


@Stable
data class CustomerListUIState(
    var customerList: List<CustomerEntity> = emptyList(),
    var isFetchingFruits: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var customerSearchQuery: String = "",
    var errorMessage: String? = null,
    var connectivityStatus: String? = null,
    var selectedDateRange: Pair<Long?, Long?> = null to null,
    ){
    companion object {
        val default = CustomerListUIState()
    }
}