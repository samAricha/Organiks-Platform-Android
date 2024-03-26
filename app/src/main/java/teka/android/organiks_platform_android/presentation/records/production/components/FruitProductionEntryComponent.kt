package teka.android.organiks_platform_android.presentation.records.production.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import teka.android.organiks_platform_android.data.room.models.FruitType
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.ui.theme.Poppins
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.buttonShapes
import java.util.*

@Composable
fun FruitProductionEntryComponent(
    state: ProductionRecordingState,
    onDateSelected: (Date) -> Unit,
    onFruitTypeChange:(String) -> Unit,
    onCollectionQuantityChange:(String) -> Unit,
    onSaveFruitCollection: () -> Unit,
    updateFruitCollectionQty:() -> Unit,
    navController: NavController
){

    val fruitTypeItems = listOf(
        FruitType(1, "Tamarillo"),
        FruitType(2, "Mangoes"),
        FruitType(3, "Oranges")
    )
    var selectedFruitTypeItem by remember { mutableStateOf(fruitTypeItems[0]) }

    var expanded by remember { mutableStateOf(false) }

    state.eggTypeName = selectedFruitTypeItem.name


    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
    ) {

        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            //Text("Selected Item: ${selectedEggTypeItem.name}")

            Row(modifier = Modifier
                .width(200.dp)
                .height(40.dp)
                .background(Color.LightGray, Shapes.large)
                .clickable { expanded = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                Text(
                    text = selectedFruitTypeItem.name,
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
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                fruitTypeItems.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onFruitTypeChange(item.name)
                        selectedFruitTypeItem = item
                        expanded = false
                    }) {
                        Text(item.name)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(24.dp))


        TextField(
            value = state.fruitCollectionQty,
            label = { Text(text = "Total Fruits Collected(Kgs)") },
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
        Spacer(modifier = Modifier.height(4.dp))
        val buttonTitle = if (state.isUpdatingItem) "Update"
        else "Save"

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .width(155.dp),
                onClick ={
                    when(state.isUpdatingItem){
                        true -> {
                            updateFruitCollectionQty.invoke()
                        }
                        false -> {
                            onSaveFruitCollection.invoke()
                        }
                    }
//                    navigateUp.invoke()
                    navController.navigate(AppScreens.ProductionHome.route)
                },
                enabled = state.fruitCollectionQty.isNotEmpty(),
                shape = buttonShapes.large,
            ) {
                Text(text = buttonTitle, fontFamily = Poppins)
            }
        }
    }
}


