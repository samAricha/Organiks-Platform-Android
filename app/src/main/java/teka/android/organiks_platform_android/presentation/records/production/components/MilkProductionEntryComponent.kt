package teka.android.organiks_platform_android.presentation.records.production.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingState
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.theme.PlaceholderColor
import teka.android.organiks_platform_android.ui.theme.Poppins
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import java.util.*

@Composable
fun MilkProductionEntryComponent(
    state: ProductionRecordingState,
    onDateSelected: (Date) -> Unit,
    onEggTypeChange:(String) -> Unit,
    onCollectionQuantityChange:(String) -> Unit,
    onCrackedQuantityChange:(String) -> Unit,
    onCategoryChange:(Category) -> Unit,
    onDialogDismissed:(Boolean) -> Unit,
    onSaveEggType:() -> Unit,
    onSaveEggCollection: () -> Unit,
    updateEggCollectionQty:() -> Unit,
    navigateUp: () -> Unit
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
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = Shapes.large
        )

        Spacer(modifier = Modifier.size(24.dp))

        TextField(
            value = state.eggsCracked,
            label = { Text(text = "Cracked Eggs") },
            onValueChange = {onCrackedQuantityChange(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = Shapes.large
        )

        Spacer(modifier = Modifier.size(24.dp))


        Row(
            horizontalArrangement = Arrangement.End
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
                eggTypeItems.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onEggTypeChange(item.name)
                        selectedEggTypeItem = item
                        expanded = false
                    }) {
                        Text(item.name)
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(84.dp))


        val buttonTitle = if (state.isUpdatingItem) "Update"
        else "Save"

        Button(
            onClick ={
                when(state.isUpdatingItem){
                    true -> {
                        updateEggCollectionQty.invoke()
                    }
                    false -> {
                        onSaveEggCollection.invoke()
                    }
                }
                navigateUp.invoke()
            },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = state.eggCollectionQty.isNotEmpty()&&
                    state.eggsCracked.isNotEmpty()&&
                    state.eggTypeName.isNotEmpty(),
            shape = Shapes.large,
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text(text = buttonTitle, fontFamily = Poppins)

        }


    }


}


@Preview(showBackground = true)
@Composable
fun MilkProductionPreview() {
    MilkProductionEntryComponent(
        state = ProductionRecordingState(),
        onDateSelected = {},
        onEggTypeChange = {},
        onCollectionQuantityChange = {},
        onCrackedQuantityChange = {},
        onCategoryChange = {},
        onDialogDismissed = {},
        onSaveEggType = { /*TODO*/ },
        updateEggCollectionQty = {},
        onSaveEggCollection = {}
    ) {

    }
}


