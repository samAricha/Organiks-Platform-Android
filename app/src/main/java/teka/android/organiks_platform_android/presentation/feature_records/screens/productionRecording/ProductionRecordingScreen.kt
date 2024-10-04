package teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.components.EggProductionEntryComponent
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.components.FruitProductionEntryComponent
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.components.MilkProductionEntryComponent
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.util.components.JoiningDateDatePicker
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductionRecordingScreen(
    id: Int,
    navController: NavController
){
    val viewModel: ProductionRecordingViewModel = hiltViewModel()
    val showTaskDatePickerDialog = viewModel.showTaskDatePickerDialog.collectAsState().value


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )
    if (showTaskDatePickerDialog) {
        JoiningDateDatePicker(
            datePickerState = datePickerState,
            dismiss = {
                viewModel.setShowTaskDatePickerDialog(false)
            },
            onConfirmDate = {
                viewModel.setCollectionDate(it)
                viewModel.setShowTaskDatePickerDialog(false)
            },
        )
    }

    Scaffold() {
        ProductionRecording(
            state = viewModel.state,
            onCategoryChange = viewModel::onCategoryChange,
            viewModel = viewModel,
            navController = navController
        )
    }
}


@Composable
fun ProductionRecording(
    state: ProductionRecordingState,
    onCategoryChange: (Category) -> Unit,
    viewModel: ProductionRecordingViewModel,
    navController: NavController
) {


    Column(Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        // Production Category Section
        LazyRow {
            items(Utils.productionCategory) { category: Category ->
                CategoryItem(
                    iconRes = category.resId,
                    title = category.title,
                    selected = category == state.selectedProductionCategory
                ) {
                    onCategoryChange(category)
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        // Conditional Entry Component
        when (state.selectedProductionCategory) {
            Utils.productionCategory[0] -> {
                EggProductionEntryComponent(
                    state,
                    onCategoryChange = viewModel::onCategoryChange,
                    onDialogDismissed = viewModel::onScreenDialogDismissed,
                    onSaveEggType = viewModel::addEggCollection,
                    navController = navController,
                    viewModel = viewModel
                )
            }
            Utils.productionCategory[1] -> {
                MilkProductionEntryComponent(
                    state = ProductionRecordingState(),
                    navController = navController
                )
            }
            Utils.productionCategory[2] -> {
                FruitProductionEntryComponent(
                    state,
                    onFruitTypeChange = viewModel::onEggTypeChange,
                    onCollectionQuantityChange = viewModel::onFruitQtyChange,
                    updateFruitCollectionQty = { viewModel::updateEggCollection },
                    onSaveFruitCollection = viewModel::onSaveFruitCollection,
                    navController = navController
                )
            }
        }
    }
}





@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CategoryItem(
    @DrawableRes iconRes:Int,
    title:String,
    selected:Boolean,
    onItemClick: () -> Unit
){

    Card(
        modifier = Modifier
            .width(120.dp)
            .selectable(
                selected = selected,
                interactionSource = MutableInteractionSource(),
                indication = ripple(),
                onClick = { onItemClick.invoke() }
            ),
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.onSurface,
        ),
        shape = Shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if(selected) PrimaryColor
            else Color.LightGray,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface

        )
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Icon(painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }

    }

}
