package teka.android.organiks_platform_android.presentation.records.production.productionRecording

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import teka.android.organiks_platform_android.ui.theme.Shapes
import java.util.*

@Composable
fun ProductionRecordingScreen(
    id: Int,
    navigateUp: () -> Unit
){
    val viewModel =
        viewModel<ProductionRecordingViewModel>(factory = ProductionRecordingViewModelFactory(id))
    val state = viewModel.state
//    val onCategoryChange = viewModel::on

//    Production Category Section
//    LazyRow{
//        items(Utils.productionCategory){ category: Category ->
//            CategoryItem(iconRes = category.resId,
//                title = category.title,
//                selected = category == state.category
//            ) {
//                onCategoryChange(category)
//            }
//            Spacer(modifier = Modifier.size(16.dp))
//
//        }
//    }

//    Production Entry component

}

@Composable
fun datePickerDialog(
    context: Context,
    onDateSelected: (Date) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker,
          mYear: Int, mMonth: Int,
          mDayofMonth: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(mYear, mMonth, mDayofMonth)
            onDateSelected.invoke(calendar.time)

        }, year, month, day
    )
    return mDatePickerDialog
}


@Composable
fun CategoryItem(
    @DrawableRes iconRes:Int,
    title:String,
    selected:Boolean,
    onItemClick: () -> Unit
){

    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(
                selected = selected,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = { onItemClick.invoke() }
            ),
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colors.primary.copy(.5f)
            else MaterialTheme.colors.onSurface,
        ),
        shape = Shapes.large,
        backgroundColor = if(selected) MaterialTheme.colors.primary.copy(.5f)
        else MaterialTheme.colors.surface,
        contentColor = if (selected) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSurface

    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)) {

            Icon(painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium
            )
        }

    }

}
