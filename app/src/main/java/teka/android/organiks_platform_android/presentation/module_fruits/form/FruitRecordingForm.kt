package teka.android.organiks_platform_android.presentation.module_fruits.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import teka.android.organiks_platform_android.data.room.models.FruitType
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.module_customers.form.AddCustomerFormUIState
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.util.CustomBtn
import teka.android.organiks_platform_android.util.dialogs.CustomTimePickerDialog
import teka.android.organiks_platform_android.util.dialogs.SimpleDatePickerDialog
import teka.android.organiks_platform_android.util.formattedTimeBasedOnTimeFormat
import teka.android.organiks_platform_android.util.widgets.CustomDateBoxField
import teka.android.organiks_platform_android.util.widgets.CustomDropDown
import teka.android.organiks_platform_android.util.widgets.CustomInputTextField
import teka.android.organiks_platform_android.util.widgets.CustomTimeBoxField
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitRecordingForm(
    navController: NavController,
    viewModel: FruitRecordingFormViewModel = hiltViewModel()
){

    val fruitRecordingFormUiState = viewModel.fruitRecordingFormUIState.collectAsState().value

    val showDatePickerDialog = fruitRecordingFormUiState.showDatePickerDialog
    val showTimePickerDialog = fruitRecordingFormUiState.showTimePickerDialog

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )
    if (showDatePickerDialog) {
        SimpleDatePickerDialog(
            datePickerState = datePickerState,
            dismiss = {
                viewModel.updateModelField(FruitRecordingFormUIState::showDatePickerDialog, false)
            },
            onConfirmDate = {
                viewModel.updateModelField(FruitRecordingFormUIState::date, it)
                viewModel.updateModelField(FruitRecordingFormUIState::showDatePickerDialog, false)
            },
        )
    }


    val currentTime: Calendar = Calendar.getInstance()
    val timePickerState: TimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    if (showTimePickerDialog) {
        CustomTimePickerDialog(
            timePickerState = timePickerState,
            onDismiss = {
                viewModel.updateModelField(FruitRecordingFormUIState::showTimePickerDialog, false)
            },
            onConfirmTime = {
                viewModel.updateModelField(FruitRecordingFormUIState::time, it)
                viewModel.updateModelField(FruitRecordingFormUIState::showTimePickerDialog, false)
            }
        )
    }


    val fruitTypeItems = listOf(
        FruitType(1, "Tamarillo"),
        FruitType(2, "Mangoes"),
        FruitType(3, "Oranges")
    )

    Column(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomDateBoxField(
                modifier = Modifier.weight(1f),
                currentTextState = fruitRecordingFormUiState.date.date.toString(),
                onClick = {
                    viewModel.updateModelField(FruitRecordingFormUIState::showDatePickerDialog, true)
                },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                ),
                shape = Shapes.small
            )
            CustomTimeBoxField(
                modifier = Modifier.weight(1f),
                currentTextState = fruitRecordingFormUiState.time.formattedTimeBasedOnTimeFormat(12),
                onClick = {
                    viewModel.updateModelField(FruitRecordingFormUIState::showTimePickerDialog, true)
                },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                ),
                shape = Shapes.medium
            )
        }
        Spacer(modifier = Modifier.size(12.dp))

        CustomDropDown(
            labelText = "Fruit Type",
            options = fruitTypeItems,
            selectedOption = fruitRecordingFormUiState.fruitType,
            onOptionSelected = { selectedOption ->
                viewModel.updateStringField(FruitRecordingFormUIState::fruitType, selectedOption.name)
            },
            optionTextProvider = { option ->
                Text(option.name)
            }
        )

        Spacer(modifier = Modifier.size(12.dp))


        CustomInputTextField(
            labelText = "Fruit Weight(kgs)",
            value = fruitRecordingFormUiState.fruitWeight,
            onValueChange = {
                viewModel.updateStringField(FruitRecordingFormUIState::fruitWeight, it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        Spacer(modifier = Modifier.height(34.dp))
        val buttonTitle = if (fruitRecordingFormUiState.isUpdatingItem) "Update" else "Save"
        CustomBtn(
            onClick ={
                when(fruitRecordingFormUiState.isUpdatingItem){
                    true -> {
                        viewModel.saveFruitCollection()
                    }
                    false -> {
                        viewModel.saveFruitCollection()
                    }
                }
                navController.navigate(AppScreens.FruitListScreen.route)
            },
            btnText = buttonTitle
        )
    }
}


