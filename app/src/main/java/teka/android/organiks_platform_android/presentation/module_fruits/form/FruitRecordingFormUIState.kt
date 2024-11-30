package teka.android.organiks_platform_android.presentation.module_fruits.form

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.today


@Stable
data class FruitRecordingFormUIState(
    var fruitType: TextFieldStateMngr = (TextFieldStateMngr(labelText = "fruit")),
    var fruitWeight: TextFieldStateMngr = (TextFieldStateMngr(labelText = "fruit weight")),
    var binType: TextFieldStateMngr = TextFieldStateMngr(labelText = "Bin Type"),
    var chamberSpot: TextFieldStateMngr = TextFieldStateMngr(labelText = "Chamber Spot"),
    var binNumber: TextFieldStateMngr = TextFieldStateMngr(labelText = "Bin Number"),
    var isSavingFormData: Boolean = false,
    var isUpdatingItem: Boolean = false,
    var showDatePickerDialog: Boolean = false,
    var showTimePickerDialog: Boolean = false,
    var date: LocalDateTime = today(),
    var time: LocalTime = today().time,
    ){
    companion object {
        val default = FruitRecordingFormUIState()
    }
}