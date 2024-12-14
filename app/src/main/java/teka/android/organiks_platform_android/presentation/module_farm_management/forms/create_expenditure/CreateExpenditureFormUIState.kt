package teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_expenditure

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.data.room.entities.FarmEntity
import teka.android.organiks_platform_android.presentation.module_farm_management.components.ExpenseCategory
import teka.android.organiks_platform_android.presentation.module_farm_management.components.ExpenseSubcategory
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.today


@Stable
data class CreateExpenditureFormUIState(
    var fruitType: TextFieldStateMngr = (TextFieldStateMngr(labelText = "fruit")),
    var customerPhone: TextFieldStateMngr = (TextFieldStateMngr(labelText = "phone")),
    var farmName: TextFieldStateMngr = TextFieldStateMngr(labelText = "Farm Name"),
    var customerEmail: TextFieldStateMngr = TextFieldStateMngr(labelText = "Email"),
    var amount: TextFieldStateMngr = TextFieldStateMngr(labelText = "Amount"),
    var currentFarm: FarmEntity? = null,
    var selectedCategory: ExpenseCategory? = null,
    var selectedSubcategory: ExpenseSubcategory? = null,
    var isFetchingData: Boolean = false,
    var isSavingFormData: Boolean = false,
    var isUpdatingItem: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var showTimePickerDialog: Boolean = false,
    var showCategoryBottomSheet: Boolean = false,
    var showSubCategoryBottomSheet: Boolean = false,
    var date: LocalDateTime = today(),
    var time: LocalTime = today().time
){
    companion object {
        val default = CreateExpenditureFormUIState()
    }
}