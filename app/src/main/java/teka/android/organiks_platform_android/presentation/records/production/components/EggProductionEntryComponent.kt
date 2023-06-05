package teka.android.organiks_platform_android.presentation.records.production.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onQtyChange:(String) -> Unit,
    onCategoryChange:(Category) -> Unit,
    onDialogDismissed:(Boolean) -> Unit,
    onSaveEggType:() -> Unit,
    updateEggCollectionQty:() -> Unit,
){

    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(4.dp)
    ) {


        TextField(
            value = state.eggCollectionQty,
            onValueChange = {}, //{ onItemChange(it) },
            label = { Text(text = "Total Eggs Collected") },
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
            value = state.eggCollectionQty,
            onValueChange = {}, //{ onItemChange(it) },
            label = { Text(text = "Cracked Eggs") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = Shapes.large
        )

        Spacer(modifier = Modifier.size(12.dp))


        Row() {


            TextField(
                value = state.eggTypeName,
                onValueChange = {
                    if (isNewEnabled) onEggTypeChange.invoke(it)
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.large,
                label = { Text(text = "Egg Type") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                        }


                    )
                }
            )

            if (!state.isScreenDialogDismissed) {
                Popup(
                    onDismissRequest = {
                        onDialogDismissed.invoke(
                            !state.isScreenDialogDismissed
                        )
                    }
                ) {
                    Surface(modifier = Modifier.padding(16.dp)) {
                        Column {
                            state.eggTypes.forEach {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            onEggTypeChange.invoke(it.name)
                                            onDialogDismissed(!state.isScreenDialogDismissed)
                                        }
                                )
                            }
                        }

                    }

                }
            }

            TextButton(onClick = {
                isNewEnabled = if (isNewEnabled) {
                    onSaveEggType.invoke()
                    !isNewEnabled
                } else {
                    !isNewEnabled
                }
            }) {
                Text(text = if (isNewEnabled) "Save" else "New")
            }
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
        enabled = state.eggCollectionQty.isNotEmpty() &&
                state.eggTypes.isNotEmpty(),
//                &&
//                state.eggsCracked.isNotEmpty(),
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
        onQtyChange = {},
        onCategoryChange = {},
        onDialogDismissed = {},
        onSaveEggType = { /*TODO*/ }
    ) {

    }
}
