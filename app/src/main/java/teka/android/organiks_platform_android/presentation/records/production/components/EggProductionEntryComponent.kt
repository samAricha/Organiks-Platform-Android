package teka.android.organiks_platform_android.presentation.records.production.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.theme.Shapes
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EggProductionEntryComponent(
    state: ProductionRecordingState,
    onDateSelected: (Date) -> Unit,
    onEggTypeChange:(String) -> Unit,
    onCollectionQuantityChange:(String) -> Unit,
    onCrackedQuantityChange:(String) -> Unit,
    onCategoryChange:(Category) -> Unit,
    onDialogDismissed:(Boolean) -> Unit,
    onSaveEggType:() -> Unit,
    updateEggCollectionQty:() -> Unit,
){

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
        modifier = Modifier.padding(16.dp)
    ) {

        TextField(
            value = state.eggCollectionQty,
            label = { Text(text = "Total Eggs Collected") },
            onValueChange = {onCollectionQuantityChange(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = Shapes.large
        )

        Spacer(modifier = Modifier.size(12.dp))

        TextField(
            value = state.eggsCracked,
            label = { Text(text = "Cracked Eggs") },
            onValueChange = {onCrackedQuantityChange(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = Shapes.large
        )

        Spacer(modifier = Modifier.size(12.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceBetween
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
                    text = selectedEggTypeItem.name,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterVertically)
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
                eggTypeItems.forEach { item ->
                    DropdownMenuItem(onClick = {
                        selectedEggTypeItem = item
                        expanded = false
                    }) {
                        Text(item.name)
                    }
                }
            }


//            TextField(
//                value = state.eggTypeName,
//                onValueChange = {
//                    if (isNewEnabled) onEggTypeChange.invoke(it)
//                },
//                modifier = Modifier.weight(1f),
//                colors = TextFieldDefaults.textFieldColors(
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent
//                ),
//                shape = Shapes.large,
//                label = { Text(text = "Egg Type") },
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.KeyboardArrowDown,
//                        contentDescription = null,
//                        modifier = Modifier.clickable {
//                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
//                        }
//
//
//                    )
//                }
//            )

//            if (!state.isScreenDialogDismissed) {
//                Popup(
//                    onDismissRequest = {
//                        onDialogDismissed.invoke(
//                            !state.isScreenDialogDismissed
//                        )
//                    }
//                ) {
//                    Surface(modifier = Modifier.padding(16.dp)) {
//                        Column {
//                            state.eggTypes.forEach {
//                                Text(
//                                    text = it.name,
//                                    modifier = Modifier
//                                        .padding(8.dp)
//                                        .clickable {
//                                            onEggTypeChange.invoke(it.name)
//                                            onDialogDismissed(!state.isScreenDialogDismissed)
//                                        }
//                                )
//                            }
//                        }
//
//                    }
//
//                }
//            }

//            TextButton(onClick = {
//                isNewEnabled = if (isNewEnabled) {
//                    onSaveEggType.invoke()
//                    !isNewEnabled
//                } else {
//                    !isNewEnabled
//                }
//            }) {
//                Text(text = if (isNewEnabled) "Save" else "New")
//            }
        }
    }

    val buttonTitle = if (state.isUpdatingItem) "Update item"
    else "Add item"


    Button(
        onClick ={
            when(state.isUpdatingItem){
                true -> {
                    updateEggCollectionQty.invoke()
                }
                false -> {
                    onSaveEggType.invoke()
                }
            }
            //navigateUp.invoke()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = state.eggCollectionQty.isNotEmpty()&&
                state.eggsCracked.isNotEmpty()&&
        state.eggTypes.isNotEmpty(),
        shape = Shapes.large
    ) {
        Text(text = buttonTitle)

    }
}


@Preview(showBackground = true)
@Composable
fun EggProductionPreview() {
    EggProductionEntryComponent(
        state = ProductionRecordingState(),
        onDateSelected = {},
        onEggTypeChange = {},
        onCollectionQuantityChange = {},
        onCrackedQuantityChange = {},
        onCategoryChange = {},
        onDialogDismissed = {},
        onSaveEggType = { /*TODO*/ }
    ) {

    }
}
