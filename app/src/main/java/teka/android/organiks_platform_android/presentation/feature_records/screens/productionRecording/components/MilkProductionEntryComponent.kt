package teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.domain.models.TextFieldState
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingViewModel
import teka.android.organiks_platform_android.ui.theme.Poppins
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.buttonShapes
import teka.android.organiks_platform_android.util.components.CustomDateBoxField

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
                onClick = { viewModel.setShowTaskDatePickerDialog(true) },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                ),
                shape = Shapes.large
            )
        }
        Spacer(modifier = Modifier.size(12.dp))

        TextField(
            value = viewModel.milkCollectionQtyEntered.value,
            label = { Text(text = "Total Milk Collected(litres)") },
            onValueChange = {
                viewModel.onMilkCollectionQuantityChange(it)
                isButtonEnabled = it.isNotEmpty()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = Shapes.large
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
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .width(155.dp),
                onClick ={
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
                enabled = isButtonEnabled, // Enable the button based on isButtonEnabled

                shape = buttonShapes.large,
            ) {
                Text(text = buttonTitle, fontFamily = Poppins)
            }
        }
    }
}



