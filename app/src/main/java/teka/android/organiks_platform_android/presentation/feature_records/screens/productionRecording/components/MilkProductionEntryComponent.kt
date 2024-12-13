package teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.domain.models.TextFieldState
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingViewModel
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.quicksand
import teka.android.organiks_platform_android.util.components.CustomDateBoxField
import teka.android.organiks_platform_android.util.components.CustomInputTextField

@Composable
fun MilkProductionEntryComponent(
    state: ProductionRecordingState,
    navController: NavController
){

    // Use hiltViewModel() to inject the ViewModel
    val viewModel: ProductionRecordingViewModel = hiltViewModel()
    var isButtonEnabled by remember { mutableStateOf(false) }
    val collectionDate = viewModel.collectionDate.collectAsState().value


    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CustomDateBoxField(
                currentTextState = TextFieldState(
                    text = collectionDate.date.toString(),
                ),
                onClick = {
                    viewModel.setShowTaskDatePickerDialog(true)
                          },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                ),
                shape = Shapes.large
            )
        }
        Spacer(modifier = Modifier.size(12.dp))

        CustomInputTextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            label = {
                Text(
                    text = "Total Milk Collected(litres):",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        fontFamily = quicksand
                    ),
                )
            },
            value = TextFieldState(text = viewModel.milkCollectionQtyEntered.value),
            onValueChange = {
                viewModel.onMilkCollectionQuantityChange(it)
                isButtonEnabled = it.isNotEmpty()
                            },
            placeholder = {
                Text(
                    text = "Litres of Milk",
                    style = MaterialTheme.typography.titleSmall,
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
            ),
        )
        Spacer(modifier = Modifier.height(34.dp))


        Canvas(
            modifier = Modifier.fillMaxWidth()
        ) {
            val startY = size.height / 2f
            val startX = 0f
            val endX = size.width

            drawLine(
                color = Color.Black, // change the color here
                start = Offset(startX, startY),
                end = Offset(endX, startY),
                strokeWidth = 2f, //change the line thickness here
                cap = StrokeCap.Round
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
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
//                            updateEggCollectionQty.invoke()
                        }
                        false -> {
                            viewModel.saveMilkCollection()
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Record saved successfully",
                                    actionLabel = "Dismiss"
                                )
                            }
                        }
                    }
                    navController.navigate(AppScreens.ProductionHome.route)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = isButtonEnabled,
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



