package teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_farm

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.presentation.module_farm_management.components.FarmCategory
import teka.android.organiks_platform_android.presentation.module_farm_management.components.FarmSubcategory
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.today


@Stable
data class CreateFarmFormUIState(
    var fruitType: TextFieldStateMngr = (TextFieldStateMngr(labelText = "fruit")),
    var customerPhone: TextFieldStateMngr = (TextFieldStateMngr(labelText = "phone")),
    var farmName: TextFieldStateMngr = TextFieldStateMngr(labelText = "Farm Name"),
    var customerEmail: TextFieldStateMngr = TextFieldStateMngr(labelText = "Email"),
    var selectedCategory: FarmCategory? = null,
    var selectedFarmSubcategory: FarmSubcategory? = null,
    var isSavingFormData: Boolean = false,
    var isUpdatingItem: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var showTimePickerDialog: Boolean = false,
    var showCategoryBottomSheet: Boolean = false,
    var showSubCategoryBottomSheet: Boolean = false,
    var date: LocalDateTime = today(),
    var time: LocalTime = today().time,
    ){
    companion object {
        val default = CreateFarmFormUIState()
    }
}