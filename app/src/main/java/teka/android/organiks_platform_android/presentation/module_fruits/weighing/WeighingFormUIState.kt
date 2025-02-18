package teka.android.organiks_platform_android.presentation.module_fruits.weighing

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.entities.WeighingEntity
import teka.android.organiks_platform_android.presentation.module_customers.form.AddCustomerFormUIState
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.today


@Stable
data class WeighingFormUIState(
    var fruitWeight: TextFieldStateMngr = (TextFieldStateMngr(labelText = "fruit weight")),
    var weightList: List<WeighingEntity> = emptyList(),
    var isSavingFormData: Boolean = false,
    var isUpdatingItem: Boolean = false,
    var isFetchingWeights: Boolean = false,
    var date: LocalDateTime = today(),
    var time: LocalTime = today().time,
    ){
    companion object {
        val default = AddCustomerFormUIState()
    }
}