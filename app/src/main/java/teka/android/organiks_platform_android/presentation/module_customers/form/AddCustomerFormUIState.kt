package teka.android.organiks_platform_android.presentation.module_customers.form

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.today


@Stable
data class AddCustomerFormUIState(
    var fruitType: TextFieldStateMngr = (TextFieldStateMngr(labelText = "fruit")),
    var customerPhone: TextFieldStateMngr = (TextFieldStateMngr(labelText = "phone")),
    var customerName: TextFieldStateMngr = TextFieldStateMngr(labelText = "Name"),
    var customerEmail: TextFieldStateMngr = TextFieldStateMngr(labelText = "Email"),
    var isSavingFormData: Boolean = false,
    var isUpdatingItem: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var showTimePickerDialog: Boolean = false,
    var date: LocalDateTime = today(),
    var time: LocalTime = today().time,
    ){
    companion object {
        val default = AddCustomerFormUIState()
    }
}