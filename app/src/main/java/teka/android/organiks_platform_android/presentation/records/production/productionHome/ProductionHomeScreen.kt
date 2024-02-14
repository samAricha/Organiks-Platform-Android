package teka.android.organiks_platform_android.presentation.records.production.productionHome

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import teka.android.organiks_platform_android.ui.theme.PoppinsExtraLight
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.PathEffect
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductionHomeScreen(
    onNavigate:(Int) -> Unit
){
    var selectedCategory by remember { mutableStateOf(Utils.productionCategory[0]) }
    val productionHomeViewModel : ProductionHomeViewModel = hiltViewModel()
    val eggCollections by productionHomeViewModel.eggCollections.collectAsState()


    val isSyncing by productionHomeViewModel.isSyncing.collectAsState()
    val fabClicked = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    val snackbarData by productionHomeViewModel.snackbarData.collectAsState()

    if (snackbarData != null) {
        LaunchedEffect(snackbarData) {
            scaffoldState.snackbarHostState.showSnackbar(snackbarData!!.message)
            productionHomeViewModel.clearSnackbar()
        }
    }


    val collections = when (selectedCategory) {
        Utils.productionCategory[0] -> {
            val eggCollectionsState by productionHomeViewModel.eggCollections.collectAsState()
            eggCollectionsState
        }
        Utils.productionCategory[1] -> {
            val milkCollectionsState by productionHomeViewModel.milkCollections.collectAsState()
            milkCollectionsState
        }
        Utils.productionCategory[2] -> {
            val fruitCollectionsState by productionHomeViewModel.fruitCollections.collectAsState()
            fruitCollectionsState
        }
        else -> emptyList() // Handle other categories as needed
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
        FloatingActionButton(onClick = {
            fabClicked.value = true
            productionHomeViewModel.syncRoomDbToRemote()
            fabClicked.value = false

        },
        backgroundColor = PrimaryColor) {
            Icon(painter = painterResource(R.drawable.cloud_upload),
                contentDescription = null,
            tint = Color.White
            )
        }
    },
    snackbarHost = {
        SnackbarHost(
            hostState = scaffoldState.snackbarHostState,
            modifier = Modifier.padding(16.dp)
        ) { snackbarData ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
                snackbarData = snackbarData
            )
        }
    }) {
        Box(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
        ){
            LazyColumn {
                item {
                    LazyRow(Modifier.padding(bottom = 16.dp)) {
                        items(Utils.productionCategory) { category: Category ->
                            CategoryItem(
                                iconRes = category.resId,
                                title = category.title,
                                selected = category == selectedCategory
                            ) {
                                selectedCategory = category
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                }
                items(collections) { collection ->
                    when (selectedCategory) {
                        Utils.productionCategory[0] -> {
                            EggCollectionItem(
                                eggCollection = collection as EggCollection,
                                onItemClick = { onNavigate.invoke(collection.id) }
                            )
                        }
                        Utils.productionCategory[1] -> {
                            MilkCollectionItem(
                                milkCollection = collection as MilkCollection,
                                onItemClick = { onNavigate.invoke(collection.id) }
                            )
                        }
                        Utils.productionCategory[2] -> {
                            FruitCollectionItem(
                                fruitCollection = collection as FruitCollectionEntity,
                                onItemClick = { onNavigate.invoke(collection.id) }
                            )
                        }
                        // Handle other categories as needed
                    }
                }
            }


            if (isSyncing) {
                ProgressIndicator()
            }
        }
    }
}

@Composable
fun ProgressIndicator(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryColor)
    }
}


@Composable
fun EggCollectionItem(
    eggCollection: EggCollection,
    onItemClick: () -> Unit
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }
            .padding(top = 8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            val icon = if (eggCollection.isBackedUp) {
                painterResource(R.drawable.checkmark) // "Backed Up" icon
            } else {
                painterResource(R.drawable.cloud_not_done) // "Not Backed Up" icon
            }

            Column(modifier = Modifier.padding(0.dp)) {
                Image(
                    painter = icon,
                    modifier = Modifier.size(24.dp),
                    contentDescription = if (eggCollection.isBackedUp) "Backed Up" else "Not Backed Up"
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Kienyeji",
                    fontFamily = PoppinsLight
                )
                Text(text = "Total: ${eggCollection.qty} Eggs",
                    fontFamily = PoppinsLight
                )
                Text(text = "Cracked: ${eggCollection.cracked} Eggs",
                    fontFamily = PoppinsLight
                )
            }
            Box(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()) {
                // Date Text
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(eggCollection.date)
                Text(text = formattedDate,
                    fontFamily = PoppinsExtraLight,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

        }

    }
}



@Composable
fun MilkCollectionItem(milkCollection: MilkCollection, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = if (milkCollection.isBackedUp) {
                painterResource(R.drawable.checkmark) // "Backed Up" icon
            } else {
                painterResource(R.drawable.cloud_not_done) // "Not Backed Up" icon
            }

            Column(modifier = Modifier.padding(0.dp)) {
                Image(
                    painter = icon,
                    modifier = Modifier.size(24.dp),
                    contentDescription = if (milkCollection.isBackedUp) "Backed Up" else "Not Backed Up"
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Milk", // You can customize the text as needed
                    fontFamily = PoppinsLight
                )
                Text(
                    text = "Qty: ${milkCollection.qty} litres",
                    fontFamily = PoppinsLight
                )
                // Add more properties as needed
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            ) {
                // Date Text
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(milkCollection.date)
                Text(
                    text = formattedDate,
                    fontFamily = PoppinsExtraLight,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}


@Composable
fun FruitCollectionItem(fruitCollection: FruitCollectionEntity, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = if (fruitCollection.isBackedUp) {
                painterResource(R.drawable.checkmark) // "Backed Up" icon
            } else {
                painterResource(R.drawable.cloud_not_done) // "Not Backed Up" icon
            }

            Column(modifier = Modifier.padding(0.dp)) {
                Image(
                    painter = icon,
                    modifier = Modifier.size(24.dp),
                    contentDescription = if (fruitCollection.isBackedUp) "Backed Up" else "Not Backed Up"
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Fruits", // You can customize the text as needed
                    fontFamily = PoppinsLight
                )
                Text(
//                    text = "Qty: ${fruitCollection.qty} litres",
                    text = "Qty: ${fruitCollection.qty} litres",
                    fontFamily = PoppinsLight
                )
                // Add more properties as needed
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            ) {
                // Date Text
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(fruitCollection.date)
                Text(
                    text = formattedDate,
                    fontFamily = PoppinsExtraLight,
                    modifier = Modifier.align(Alignment.BottomEnd)
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
            .width(120.dp)
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
        backgroundColor = if(selected) PrimaryColor
        else Color.LightGray,
        contentColor = if (selected) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSurface

    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {

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

@Composable
private fun DrawVerticalDashLine() {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
    Canvas(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.5.dp)
    ) {
        drawLine(
            color = Color.Black, // Line color
            strokeWidth = 5f, // Line width
            start = Offset(0f, 0f), // Starting point (left side)
            end = Offset(0f, size.height), // Ending point (bottom)
            pathEffect = pathEffect
        )
    }
}



