package teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_expenditure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import teka.android.organiks_platform_android.presentation.module_farm_management.components.ExpenseCategory
import teka.android.organiks_platform_android.presentation.module_farm_management.components.ExpenseSubcategory
import teka.android.organiks_platform_android.presentation.module_farm_management.components.expenseCategories
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
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpenditureForm(
    navController: NavController,
    viewModel: CreateExpenditureFormViewModel = hiltViewModel()
){

    val expenditureFormUiState = viewModel.expenditureFormUiState.collectAsState().value
    val showDatePickerDialog = expenditureFormUiState.showDatePickerDialog
    val showTimePickerDialog = expenditureFormUiState.showTimePickerDialog

    val fruitExpenses = expenseCategories.filter { it.type == "Fruit" }
    val poultryExpenses = expenseCategories.filter { it.type == "Poultry" }
    val generalExpenses = expenseCategories.filter { it.type == "General" }


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )
    if (showDatePickerDialog) {
        SimpleDatePickerDialog(
            datePickerState = datePickerState,
            dismiss = {
                viewModel.updateModelField(CreateExpenditureFormUIState::showDatePickerDialog, false)
            },
            onConfirmDate = {
                viewModel.updateModelField(CreateExpenditureFormUIState::date, it)
                viewModel.updateModelField(CreateExpenditureFormUIState::showDatePickerDialog, false)
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
                viewModel.updateModelField(CreateExpenditureFormUIState::showTimePickerDialog, false)
            },
            onConfirmTime = {
                viewModel.updateModelField(CreateExpenditureFormUIState::time, it)
                viewModel.updateModelField(CreateExpenditureFormUIState::showTimePickerDialog, false)
            }
        )
    }


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
                        currentTextState = expenditureFormUiState.date.date.toString(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateExpenditureFormUIState::showDatePickerDialog,
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
                        currentTextState = expenditureFormUiState.time.formattedTimeBasedOnTimeFormat(
                            12
                        ),
                        onClick = {
                            viewModel.updateModelField(
                                CreateExpenditureFormUIState::showTimePickerDialog,
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

            item{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BottomSheetTextField(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        labelText = "Category",
                        placeholderText = "Category",
                        currentTextState = expenditureFormUiState.selectedCategory?.name.orEmpty(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateExpenditureFormUIState::showCategoryBottomSheet,
                                true
                            )
                        }
                    )

                    BottomSheetTextField(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        labelText = "SubType",
                        placeholderText = "SubType",
                        currentTextState = expenditureFormUiState.selectedSubcategory?.name.orEmpty(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateExpenditureFormUIState::showSubCategoryBottomSheet,
                                true
                            )
                        }
                    )
                }
            }

            item{
                CustomInputTextField(
                    labelText = "Amount",
                    value = expenditureFormUiState.amount,
                    onValueChange = {
                        viewModel.updateStringField(CreateExpenditureFormUIState::amount, it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                Spacer(modifier = Modifier.height(34.dp))
            }
            item {
                val buttonTitle =
                    if (expenditureFormUiState.isUpdatingItem) "Update Expenditure" else "Create Expenditure"
                CustomBtn(
                    onClick = {
                        when (expenditureFormUiState.isUpdatingItem) {
                            true -> {
                                viewModel.saveExpenseEntity()
                            }
                            false -> {
                                viewModel.saveExpenseEntity()
                            }
                        }
                        navController.popBackStack()
                    },
                    btnText = buttonTitle
                )
            }
        }

        BottomSheetSelection(
            visible = expenditureFormUiState.showCategoryBottomSheet,
            title = "Expense Category",
            items = fruitExpenses,
            searchValue = "",
            onSearchValueChange ={ query ->
            },
            onDismissRequest = {
                viewModel.updateModelField(CreateExpenditureFormUIState::showCategoryBottomSheet, false)
            },
            onItemSelected = { selectedCategory ->
                viewModel.updateModelField(CreateExpenditureFormUIState::selectedCategory, selectedCategory)
                viewModel.updateModelField(CreateExpenditureFormUIState::showCategoryBottomSheet, false)
            },
            itemContent = { category ->
                ExpenseCategoryItem(category) {
                    viewModel.updateModelField(CreateExpenditureFormUIState::selectedCategory, category)
                    viewModel.updateModelField(CreateExpenditureFormUIState::showCategoryBottomSheet, false)
                }
            },
            isSearchEnabled = false
        )


        BottomSheetSelection(
            visible = expenditureFormUiState.showSubCategoryBottomSheet,
            title = "Expense Sub-Category",
            items = expenditureFormUiState.selectedCategory?.subcategories ?: emptyList(),
            searchValue = "",
            onSearchValueChange ={ query ->
            },
            onDismissRequest = {
                viewModel.updateModelField(CreateExpenditureFormUIState::showSubCategoryBottomSheet, false)
            },
            onItemSelected = { selectedCategory ->
                viewModel.updateModelField(CreateExpenditureFormUIState::selectedSubcategory, selectedCategory)
                viewModel.updateModelField(CreateExpenditureFormUIState::showSubCategoryBottomSheet, false)
            },
            itemContent = { subCategory ->
                ExpenseSubCategoryItem(subCategory) {
                    viewModel.updateModelField(CreateExpenditureFormUIState::selectedSubcategory, subCategory)
                    viewModel.updateModelField(CreateExpenditureFormUIState::showSubCategoryBottomSheet, false)
                }
            },
            isSearchEnabled = false
        )


    }
}



@Composable
fun ExpenseCategoryItem(expenseCategory: ExpenseCategory, onBookClick: () -> Unit) {
    val categoryNumber = " ${expenseCategory.id}."
    val titleText = expenseCategory.name
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onBookClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle()) {
                        append(" $titleText")
                    }
                }
            )
        }
    }
}


@Composable
fun ExpenseSubCategoryItem(expenseSubCategory: ExpenseSubcategory, onBookClick: () -> Unit) {
    val categoryNumber = " ${expenseSubCategory.id}."
    val titleText = expenseSubCategory.name
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onBookClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle()) {
                        append(" $titleText")
                    }
                }
            )
        }
    }
}


