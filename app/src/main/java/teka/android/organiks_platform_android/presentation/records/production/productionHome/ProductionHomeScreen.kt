package teka.android.organiks_platform_android.presentation.records.production.productionHome

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.room.EggTypeEggCollectionItem
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import teka.android.organiks_platform_android.ui.theme.PoppinsExtraLight
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import teka.android.organiks_platform_android.ui.theme.ReemKufi
import teka.android.organiks_platform_android.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductionHomeScreen(
    onNavigate:(Int) -> Unit
){

    val productionHomeViewModel = viewModel(modelClass = ProductionHomeViewModel::class.java)
    val productionHomeState = productionHomeViewModel.state

    println("ALERT ALERT ${ productionHomeState.eggCollectionsWithTypesList.size }")
    Log.d(TAG, "INSIDE GET EGG COLLECTION${ productionHomeState.eggCollections }")


    Scaffold(floatingActionButton = {

        FloatingActionButton(onClick = { productionHomeViewModel.syncRoomDbToRemote() }) {
            Icon(painter = painterResource(R.drawable.cloud_upload),
                contentDescription = null,
            tint = Color.White
            )
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

            items(productionHomeState.eggCollections){

                EggCollectionItems(
                    eggCollection = it,
                    onCheckedChange = productionHomeViewModel::onEggCollectionCheckedChange
                ) {
                    onNavigate.invoke(it.id)
                }

            }


        }

    }


}



@Composable
fun EggCollectionItems(
    eggCollection: EggCollection,
    onCheckedChange: (EggCollection, Boolean) -> Unit,
    onItemClick: () -> Unit
){

    println("ALERT3 ALERT3 ${ eggCollection.qty }")
    Log.d(TAG, "INSIDE GET EGG COLLECTION$eggCollection")


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
            val icon = if (eggCollection.isBackedUp) {
                painterResource(R.drawable.cloud_done) // "Backed Up" icon
            } else {
                painterResource(R.drawable.cloud_not_done) // "Not Backed Up" icon
            }

            Column(modifier = Modifier.padding(0.dp)) {
                Image(
                    painter = icon,
                    contentDescription = if (eggCollection.isBackedUp) "Backed Up" else "Not Backed Up"
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Kienyeji",
                    fontFamily = PoppinsLight
                )
                Text(text = "Total: ${eggCollection.qty}",
                    fontFamily = PoppinsLight
                )
                Text(text = "Cracked: ${eggCollection.cracked}",
                    fontFamily = PoppinsLight
                )
            }
            Column(modifier = Modifier.padding(8.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End) {
                // Date Text
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(eggCollection.date)
                Text(text = formattedDate,
                    fontFamily = PoppinsExtraLight,
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
            .width(200.dp)
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



