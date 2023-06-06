package teka.android.organiks_platform_android.presentation.records.production.productionHome

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import teka.android.organiks_platform_android.data.room.EggTypeEggCollectionItem
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import teka.android.organiks_platform_android.ui.theme.Shapes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductionHomeScreen(
    onNavigate:(Int) -> Unit
){

    val productionHomeViewModel = viewModel(modelClass = ProductionHomeViewModel::class.java)
    val productionHomeState = productionHomeViewModel.state

    Scaffold(floatingActionButton = {

        FloatingActionButton(onClick = { onNavigate.invoke(-1) }) {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = null)
        }

    }) {
        LazyColumn {
            item {
                LazyRow {
                    items(Utils.productionCategory) { category: Category ->
                        CategoryItem(
                            iconRes = category.resId,
                            title = category.title,
                            selected = category == productionHomeState.category
                        ) {
                            //productionHomeViewModel.onProductionCategoryChange(category)
                        }
                        Spacer(modifier = Modifier.size(16.dp))

                    }
                }
            }

            items(productionHomeState.eggCollectionsWithTypesList){

                EggCollectionItems(
                    eggCollectionWithTypeItem = it,
                    isChecked = it.eggCollection.isChecked,
                    viewModel = productionHomeViewModel,
                    onCheckedChange = productionHomeViewModel::onEggCollectionCheckedChange
                ) {
                    onNavigate.invoke(it.eggCollection.id)
                }

            }


        }

    }


}



@Composable
fun EggCollectionItems(
    eggCollectionWithTypeItem: EggTypeEggCollectionItem,
    viewModel: ProductionHomeViewModel,
    isChecked: Boolean,
    onCheckedChange: (EggCollection, Boolean) -> Unit,
    onItemClick: () -> Unit
){


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = eggCollectionWithTypeItem.eggType.name,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))

            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Qty: ${eggCollectionWithTypeItem.eggCollection.qty}",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        onCheckedChange.invoke(eggCollectionWithTypeItem.eggCollection, it)

                    },
                )
            }

        }

    }


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



