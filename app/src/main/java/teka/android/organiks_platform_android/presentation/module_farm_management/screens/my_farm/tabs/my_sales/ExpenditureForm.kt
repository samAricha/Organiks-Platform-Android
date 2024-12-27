package teka.android.organiks_platform_android.presentation.module_farm_management.screens.my_farm.tabs.my_sales

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import teka.android.organiks_platform_android.data.room.entities.FruitType
import teka.android.organiks_platform_android.presentation.module_farm_management.components.FarmCategory
import teka.android.organiks_platform_android.presentation.module_farm_management.components.FarmSubcategory
import teka.android.organiks_platform_android.presentation.module_farm_management.components.farmCategories
import teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_farm.CreateFarmFormUIState
import teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_farm.CreateFarmFormViewModel
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
fun ExpenditureForm(
    navController: NavController,
    viewModel: CreateFarmFormViewModel = hiltViewModel()
){

    val createFarmFormUiState = viewModel.createFarmFormUiState.collectAsState().value

    val showDatePickerDialog = createFarmFormUiState.showDatePickerDialog
    val showTimePickerDialog = createFarmFormUiState.showTimePickerDialog

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
                CustomInputTextField(
                    labelText = "Farm Name",
                    value = createFarmFormUiState.farmName,
                    onValueChange = {
                        viewModel.updateStringField(CreateFarmFormUIState::farmName, it)
                    },
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomDateBoxField(
                        modifier = Modifier.weight(1f),
                        currentTextState = createFarmFormUiState.date.date.toString(),
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
                        currentTextState = createFarmFormUiState.time.formattedTimeBasedOnTimeFormat(
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

            item{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BottomSheetTextField(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        labelText = "Type",
                        placeholderText = "Type",
                        currentTextState = createFarmFormUiState.selectedCategory?.name.orEmpty(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateFarmFormUIState::showCategoryBottomSheet,
                                true
                            )
                        }
                    )

                    BottomSheetTextField(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        labelText = "SubType",
                        placeholderText = "SubType",
                        currentTextState = createFarmFormUiState.selectedSubcategory?.name.orEmpty(),
                        onClick = {
                            viewModel.updateModelField(
                                CreateFarmFormUIState::showSubCategoryBottomSheet,
                                true
                            )
                        }
                    )
                }
            }


            item {
                Spacer(modifier = Modifier.height(34.dp))
            }
            item {
                val buttonTitle =
                    if (createFarmFormUiState.isUpdatingItem) "Update Farm" else "Create Farm"
                CustomBtn(
                    onClick = {
                        when (createFarmFormUiState.isUpdatingItem) {
                            true -> {
                                viewModel.saveFarmEntity()
                            }
                            false -> {
                                viewModel.saveFarmEntity()
                            }
                        }
                        navController.popBackStack()
                    },
                    btnText = buttonTitle
                )
            }
        }

        BottomSheetSelection(
            visible = createFarmFormUiState.showCategoryBottomSheet,
            title = "Farm Category",
            items = farmCategories,
            searchValue = "",
            onSearchValueChange ={ query ->
            },
            onDismissRequest = {
                viewModel.updateModelField(CreateFarmFormUIState::showCategoryBottomSheet, false)
            },
            onItemSelected = { selectedCategory ->
                viewModel.updateModelField(CreateFarmFormUIState::selectedCategory, selectedCategory)
                viewModel.updateModelField(CreateFarmFormUIState::showCategoryBottomSheet, false)
            },
            itemContent = { category ->
                FarmCategoryItem(category) {
                    viewModel.updateModelField(CreateFarmFormUIState::selectedCategory, category)
                    viewModel.updateModelField(CreateFarmFormUIState::showCategoryBottomSheet, false)
                }
            },
            isSearchEnabled = false
        )


        BottomSheetSelection(
            visible = createFarmFormUiState.showSubCategoryBottomSheet,
            title = "Farm Sub Category",
            items = createFarmFormUiState.selectedCategory?.subcategories ?: emptyList(),
            searchValue = "",
            onSearchValueChange ={ query ->
            },
            onDismissRequest = {
                viewModel.updateModelField(CreateFarmFormUIState::showSubCategoryBottomSheet, false)
            },
            onItemSelected = { selectedCategory ->
                viewModel.updateModelField(CreateFarmFormUIState::selectedSubcategory, selectedCategory)
                viewModel.updateModelField(CreateFarmFormUIState::showSubCategoryBottomSheet, false)
            },
            itemContent = { subCategory ->
                FarmSubCategoryItem(subCategory) {
                    viewModel.updateModelField(CreateFarmFormUIState::selectedSubcategory, subCategory)
                    viewModel.updateModelField(CreateFarmFormUIState::showSubCategoryBottomSheet, false)
                }
            },
            isSearchEnabled = false
        )


    }
}



@Composable
fun FarmCategoryItem(farmCategory: FarmCategory, onBookClick: () -> Unit) {
    val categoryNumber = " ${farmCategory.id}."
    val titleText = farmCategory.name
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
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(categoryNumber)
                    }
                    withStyle(style = SpanStyle()) {
                        append(" $titleText")
                    }
                }
            )
        }
    }
}


@Composable
fun FarmSubCategoryItem(expenseSubCategory: FarmSubcategory, onBookClick: () -> Unit) {
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
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(categoryNumber)
                    }
                    withStyle(style = SpanStyle()) {
                        append(" $titleText")
                    }
                }
            )
        }
    }
}


