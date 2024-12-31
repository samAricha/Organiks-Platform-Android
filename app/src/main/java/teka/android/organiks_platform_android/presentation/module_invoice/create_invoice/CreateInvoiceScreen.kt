package teka.android.organiks_platform_android.presentation.module_invoice.create_invoice

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.util.CustomBtn
import teka.android.organiks_platform_android.util.components.bottom_sheet.BottomSheetSelection
import teka.android.organiks_platform_android.util.components.bottom_sheet.BottomSheetTextField
import teka.android.organiks_platform_android.util.dialogs.CustomTimePickerDialog
import teka.android.organiks_platform_android.util.dialogs.SimpleDatePickerDialog
import teka.android.organiks_platform_android.util.formattedTimeBasedOnTimeFormat
import teka.android.organiks_platform_android.util.widgets.CustomDateBoxField
import teka.android.organiks_platform_android.util.widgets.CustomInputTextField
import teka.android.organiks_platform_android.util.widgets.CustomTimeBoxField
import timber.log.Timber
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateInvoiceScreen(
    navController: NavController,
    viewModel: CreateInvoiceViewModel = hiltViewModel()
) {
    val createInvoiceUiState = viewModel.createInvoiceUiState.collectAsState().value
    val fruitCollection = createInvoiceUiState.currentFruitCollection

    val showDatePickerDialog = createInvoiceUiState.showDatePickerDialog
    val showTimePickerDialog = createInvoiceUiState.showTimePickerDialog
    val showBottomSheet = createInvoiceUiState.showBottomSheet
    val customerEntityList = createInvoiceUiState.customerEntityList
    val context = LocalContext.current


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )
    if (showDatePickerDialog) {
        SimpleDatePickerDialog(
            datePickerState = datePickerState,
            dismiss = {
                viewModel.updateModelField(CreateInvoiceUiState::showDatePickerDialog, false)
            },
            onConfirmDate = {
                viewModel.updateModelField(CreateInvoiceUiState::date, it)
                viewModel.updateModelField(CreateInvoiceUiState::showDatePickerDialog, false)
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
                viewModel.updateModelField(CreateInvoiceUiState::showTimePickerDialog, false)
            },
            onConfirmTime = {
                viewModel.updateModelField(CreateInvoiceUiState::time, it)
                viewModel.updateModelField(CreateInvoiceUiState::showTimePickerDialog, false)
            }
        )
    }

    LaunchedEffect(createInvoiceUiState.isFormSubmissionSuccessful) {
        if (createInvoiceUiState.isFormSubmissionSuccessful) {
            Toast.makeText(context, "Invoice created successfully", Toast.LENGTH_LONG).show()
            navController.navigate(AppScreens.InvoiceListScreen.route) {
                launchSingleTop = true
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {

        if (fruitCollection != null) {
            FruitInfoItemCard(
                fruitCollectionEntity = fruitCollection,
                navController = navController
            )
        }

        LazyColumn (
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomDateBoxField(
                        modifier = Modifier.weight(1f),
                        currentTextState = createInvoiceUiState.date.date.toString(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateInvoiceUiState::showDatePickerDialog,
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
                        currentTextState = createInvoiceUiState.time.formattedTimeBasedOnTimeFormat(
                            12
                        ),
                        onClick = {
                            viewModel.updateModelField(
                                CreateInvoiceUiState::showTimePickerDialog,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomInputTextField(
                        modifier = Modifier.weight(1f),
                        labelText = "Unit Price",
                        value = createInvoiceUiState.unitPrice,
                        onValueChange = {
                            viewModel.updateStringField(CreateInvoiceUiState::unitPrice, it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    CustomInputTextField(
                        modifier = Modifier.weight(1f),
                        labelText = "Total Amount",
                        value = createInvoiceUiState.totalAmount,
                        onValueChange = {
//                            viewModel.updateStringField(CreateInvoiceUiState::totalAmount, it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        editable = false,
                        isCash = true
                    )
                }
            }
            item{
                CustomInputTextField(
                    modifier = Modifier.weight(1f),
                    labelText = "Total Expenses",
                    value = createInvoiceUiState.totalExpenses,
                    onValueChange = {
                        viewModel.updateStringField(CreateInvoiceUiState::totalExpenses, it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            item {
                BottomSheetTextField(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    labelText = "Customer",
                    placeholderText = "Customer",
                    currentTextState = createInvoiceUiState.selectedCustomer?.name.orEmpty(),
                    onClick = {
                        viewModel.updateModelField(
                            CreateInvoiceUiState::showBottomSheet,
                            true
                        )
                    }
                )
            }
            item {
                CustomInputTextField(
                    modifier = Modifier.weight(1f),
                    labelText = "Your Name",
                    value = createInvoiceUiState.issuerName,
                    onValueChange = {
                        viewModel.updateStringField(CreateInvoiceUiState::issuerName, it)
                    },
                )
            }
            item {
                CustomInputTextField(
                    modifier = Modifier.weight(1f),
                    labelText = "Your Phone",
                    value = createInvoiceUiState.issuerPhone,
                    onValueChange = {
                        viewModel.updateStringField(CreateInvoiceUiState::issuerPhone, it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            item {
                CustomInputTextField(
                    modifier = Modifier.weight(1f),
                    labelText = "Your Email",
                    value = createInvoiceUiState.issuerEmail,
                    onValueChange = {
                        viewModel.updateStringField(CreateInvoiceUiState::issuerEmail, it)
                    },
                )
            }

            item {
                Spacer(modifier = Modifier.height(34.dp))
            }
            item {
                HorizontalDivider()
                CustomBtn(
                    btnText = "Generate Invoice",
                    onClick = {
                        viewModel.createInvoice()
//                        val filePath = viewModel.generateInvoice()
//                        if (filePath != null) {
//                            Timber.tag("Invoice Screen::filePath").i(filePath)
//                            Toast.makeText(context, "Invoice saved at: $filePath", Toast.LENGTH_LONG).show()
//                            navController.navigate(AppScreens.InvoiceListScreen.route)
//                        } else {
//                            Toast.makeText(context, "Failed to generate invoice.", Toast.LENGTH_LONG).show()
//                        }
                    }
                )
            }
        }

        BottomSheetSelection(
            visible = showBottomSheet,
            title = "Person to Invoice",
            items = customerEntityList,
            searchValue = createInvoiceUiState.customerSearchQuery,
            onSearchValueChange ={ query ->
                viewModel.updateModelField(CreateInvoiceUiState::customerSearchQuery,query)
            },
            onDismissRequest = {
                viewModel.updateModelField(CreateInvoiceUiState::showBottomSheet, false)
                               },
            onItemSelected = { selectedStaff ->
                viewModel.updateModelField(CreateInvoiceUiState::selectedCustomer, selectedStaff)
                viewModel.updateModelField(CreateInvoiceUiState::showBottomSheet, false)

                             },
            itemContent = { customer ->
                Text(
                    text = customer.name.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        )



    }
}
