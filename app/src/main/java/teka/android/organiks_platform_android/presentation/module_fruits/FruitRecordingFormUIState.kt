package teka.android.organiks_platform_android.presentation.module_fruits

import androidx.compose.runtime.Stable
import teka.android.organiks_platform_android.util.TextFieldStateMngr


@Stable
data class FruitRecordingFormUIState(
    var fruitSource: TextFieldStateMngr = TextFieldStateMngr(labelText = "source"),
    var binType: TextFieldStateMngr = TextFieldStateMngr(labelText = "Bin Type"),
    var chamberSpot: TextFieldStateMngr = TextFieldStateMngr(labelText = "Chamber Spot"),
    var binNumber: TextFieldStateMngr = TextFieldStateMngr(labelText = "Bin Number"),
    var isSavingFormData: Boolean = false,
    ){
    companion object {
        val default = FruitRecordingFormUIState()
    }
}