package teka.android.organiks_platform_android.presentation.records.production.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.ui.theme.Poppins
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.buttonShapes

@Composable
fun MilkProductionEntryComponent(
    state: ProductionRecordingState,
    onCollectionQuantityChange:(String) -> Unit,
    onSaveMilkCollection: () -> Unit,
    updateMilkCollectionQty:() -> Unit,
    navigateUp: () -> Unit
){

    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        TextField(
            value = state.eggCollectionQty,
            label = { Text(text = "Total Milk Collected(litres)") },
            onValueChange = {onCollectionQuantityChange(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = Shapes.large
        )
        Spacer(modifier = Modifier.height(84.dp))


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
                modifier = Modifier.padding(horizontal = 4.dp).width(155.dp),
                onClick ={
                    when(state.isUpdatingItem){
                        true -> {
//                            updateEggCollectionQty.invoke()
                        }
                        false -> {
//                            onSaveEggCollection.invoke()
                        }
                    }
                    navigateUp.invoke()
                },
                enabled = state.eggCollectionQty.isNotEmpty()&&
                        state.eggsCracked.isNotEmpty()&&
                        state.eggTypeName.isNotEmpty(),
                shape = buttonShapes.large,
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(text = buttonTitle, fontFamily = Poppins)

            }
        }


    }


}


@Preview(showBackground = true)
@Composable
fun MilkProductionPreview() {
    MilkProductionEntryComponent(
        state = ProductionRecordingState(),
        onCollectionQuantityChange = {},
        onSaveMilkCollection = {},
        updateMilkCollectionQty = {},
        navigateUp= {},
    )
}


