package teka.android.organiks_platform_android.presentation.module_farm_management.create_farm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import teka.android.organiks_platform_android.data.room.entities.FruitType
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
import timber.log.Timber
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFarmForm(
    navController: NavController,
    viewModel: CreateFarmFormViewModel = hiltViewModel()
){

    val fruitRecordingFormUiState = viewModel.createFarmFormUiState.collectAsState().value

    val showDatePickerDialog = fruitRecordingFormUiState.showDatePickerDialog
    val showTimePickerDialog = fruitRecordingFormUiState.showTimePickerDialog

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )
    if (showDatePickerDialog) {
        SimpleDatePickerDialog(
            datePickerState = datePickerState,
            dismiss = {
                viewModel.updateModelField(CreateFarmFormUIState::showDatePickerDialog, false)
            },
            onConfirmDate = {
                viewModel.updateModelField(CreateFarmFormUIState::date, it)
                viewModel.updateModelField(CreateFarmFormUIState::showDatePickerDialog, false)
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
                viewModel.updateModelField(CreateFarmFormUIState::showTimePickerDialog, false)
            },
            onConfirmTime = {
                viewModel.updateModelField(CreateFarmFormUIState::time, it)
                viewModel.updateModelField(CreateFarmFormUIState::showTimePickerDialog, false)
            }
        )
    }


    val fruitTypeItems = listOf(
        FruitType(1, "Tamarillo"),
        FruitType(2, "Mangoes"),
        FruitType(3, "Oranges")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomDateBoxField(
                        modifier = Modifier.weight(1f),
                        currentTextState = fruitRecordingFormUiState.date.date.toString(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateFarmFormUIState::showDatePickerDialog,
                                true
                            )
                        },
                        textStyle = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                        ),
                        shape = Shapes.small
                    )
                    CustomTimeBoxField(
                        modifier = Modifier.weight(1f),
                        currentTextState = fruitRecordingFormUiState.time.formattedTimeBasedOnTimeFormat(
                            12
                        ),
                        onClick = {
                            viewModel.updateModelField(
                                CreateFarmFormUIState::showTimePickerDialog,
                                true
                            )
                        },
                        textStyle = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                        ),
                        shape = Shapes.medium
                    )
                }
            }


            item {
                CustomDropDown(
                    labelText = "Fruit Type",
                    options = fruitTypeItems,
                    selectedOption = fruitRecordingFormUiState.fruitType,
                    onOptionSelected = { selectedOption ->
                        viewModel.updateStringField(
                            CreateFarmFormUIState::fruitType,
                            selectedOption.name
                        )
                    },
                    optionTextProvider = { option ->
                        Text(option.name)
                    }
                )
            }


            item {
                CustomInputTextField(
                    labelText = "Name",
                    value = fruitRecordingFormUiState.customerName,
                    onValueChange = {
                        viewModel.updateStringField(CreateFarmFormUIState::customerName, it)
                    },
                )
            }

            item {
                CustomInputTextField(
                    labelText = "Phone",
                    value = fruitRecordingFormUiState.customerPhone,
                    onValueChange = {
                        viewModel.updateStringField(CreateFarmFormUIState::customerPhone, it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                CustomInputTextField(
                    labelText = "Email",
                    value = fruitRecordingFormUiState.customerEmail,
                    onValueChange = {
                        viewModel.updateStringField(CreateFarmFormUIState::customerEmail, it)
                    },
                )
            }

            item {
                Spacer(modifier = Modifier.height(34.dp))
            }
            item {
                val buttonTitle =
                    if (fruitRecordingFormUiState.isUpdatingItem) "Update Csutomer" else "Add Customer"
                CustomBtn(
                    onClick = {
                        when (fruitRecordingFormUiState.isUpdatingItem) {
                            true -> {
                                viewModel.saveCustomerEntity()
                            }

                            false -> {
                                viewModel.saveCustomerEntity()
                            }
                        }
                        navController.popBackStack()
                    },
                    btnText = buttonTitle
                )
            }
        }
    }
}


