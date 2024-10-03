package teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.domain.models.TextFieldState
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingViewModel
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.quicksand
import teka.android.organiks_platform_android.util.components.CustomDateBoxField
import teka.android.organiks_platform_android.util.components.CustomInputTextField
import java.util.*

@Composable
fun EggProductionEntryComponent(
    state: ProductionRecordingState,
    onCategoryChange:(Category) -> Unit,
    onDialogDismissed:(Boolean) -> Unit,
    onSaveEggType:() -> Unit,
    navController: NavController,
    viewModel: ProductionRecordingViewModel
){
    val viewModel: ProductionRecordingViewModel = hiltViewModel()
    val collectionDate = viewModel.collectionDate.collectAsState().value


    val eggTypeItems = listOf(
        EggType(1, "Kienyeji"),
        EggType(2, "Grade")
    )
    var selectedEggTypeItem by remember { mutableStateOf(eggTypeItems[0]) }

    var expanded by remember { mutableStateOf(false) }

    state.eggTypeName = selectedEggTypeItem.name


    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomDateBoxField(
                currentTextState = TextFieldState(
                    text = collectionDate.date.toString(),
                ),
                onClick = { viewModel.setShowTaskDatePickerDialog(true) },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                ),
                shape = Shapes.large
            )

            Row(modifier = Modifier
                .height(40.dp)
                .padding(horizontal = 3.dp)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = if (expanded) {
                        MaterialTheme.colorScheme.onBackground
                    } else {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = .4f)
                    },
                    shape = Shapes.large,
                )
                .clickable { expanded = true },
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    text = selectedEggTypeItem.name,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .align(Alignment.CenterVertically),
                    )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    eggTypeItems.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEggTypeChange(item.name)
                                selectedEggTypeItem = item
                                expanded = false
                            },
                            text = { Text(item.name) }
                        )
                    }
                }
            }


        }

        Spacer(modifier = Modifier.size(24.dp))
        CustomInputTextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            label = {
                Text(
                    text = "Total Eggs Collected:",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        fontFamily = quicksand
                    ),
                )
            },
            value = TextFieldState(text = state.eggCollectionQty),
            onValueChange = {viewModel.onQtyChange(it)},
            placeholder = {
                Text(
                    text = "No. of Eggs Collected",
                    style = MaterialTheme.typography.titleSmall,
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
            ),
        )
        Spacer(modifier = Modifier.size(24.dp))

        CustomInputTextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            label = {
                Text(
                    text = "Cracked Eggs:",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        fontFamily = quicksand
                    ),
                )
            },
            value = TextFieldState(text = state.eggsCracked),
            onValueChange = { viewModel.onCrackedQtyChange(it) },
            placeholder = {
                Text(
                    text = "No. of Cracked Eggs",
                    style = MaterialTheme.typography.titleSmall,
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
            ),
        )
        Spacer(modifier = Modifier.size(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

        }


        Spacer(modifier = Modifier.height(34.dp))


        Canvas(
            modifier = Modifier.fillMaxWidth()
        ) {
            val startY = size.height / 2f
            val startX = 0f
            val endX = size.width

            drawLine(
                color = Color.Black, // You can change the color here
                start = Offset(startX, startY),
                end = Offset(endX, startY),
                strokeWidth = 2f, // You can change the line thickness here
                cap = StrokeCap.Round
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        val buttonTitle = if (state.isUpdatingItem) "Update"
        else "Save"

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){

            Button(
                onClick = {
                    when(state.isUpdatingItem){
                        true -> {
                            viewModel.updateEggCollection(viewModel.eggCollectionId.value)
                        }
                        false -> {
                            viewModel.onSaveEggCollection()
                        }
                    }
                    navController.navigate(AppScreens.ProductionHome.route)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = state.eggCollectionQty.isNotEmpty()&&
                        state.eggsCracked.isNotEmpty()&&
                        state.eggTypeName.isNotEmpty(),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    text = buttonTitle,
                    color = MainWhiteColor,
                    fontFamily = quicksand,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }


}


