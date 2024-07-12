package teka.android.organiks_platform_android.presentation.feature_records.production.remoteRecords

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
import teka.android.organiks_platform_android.presentation.feature_records.production.remoteRecords.components.CategoryRowItem
import teka.android.organiks_platform_android.presentation.feature_records.production.remoteRecords.components.EggsListItem
import teka.android.organiks_platform_android.presentation.feature_records.production.remoteRecords.components.FruitsListItem
import teka.android.organiks_platform_android.presentation.feature_records.production.remoteRecords.components.MilkListItem
import teka.android.organiks_platform_android.util.components.ProgressIndicatorWidget


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RemoteRecordsScreen(
    onNavigate:(Int) -> Unit
){
    var selectedCategory by remember { mutableStateOf(Utils.productionCategory[0]) }
    val remoteRecordsViewModel : RemoteRecordsViewModel = hiltViewModel()
    val eggCollections by remoteRecordsViewModel.eggCollections.collectAsState()


    val isSyncing by remoteRecordsViewModel.isSyncing.collectAsState()
    val fabClicked = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    val snackbarData by remoteRecordsViewModel.snackbarData.collectAsState()

    if (snackbarData != null) {
        LaunchedEffect(snackbarData) {
            scaffoldState.snackbarHostState.showSnackbar(snackbarData!!.message)
            remoteRecordsViewModel.clearSnackbar()
        }
    }


    val collections = when (selectedCategory) {
        Utils.productionCategory[0] -> {
            val eggCollectionsState by remoteRecordsViewModel.eggCollections.collectAsState()
            eggCollectionsState
        }
        Utils.productionCategory[1] -> {
            val milkCollectionsState by remoteRecordsViewModel.milkCollections.collectAsState()
            milkCollectionsState
        }
        Utils.productionCategory[2] -> {
            val fruitCollectionsState by remoteRecordsViewModel.fruitCollections.collectAsState()
            fruitCollectionsState
        }
        else -> emptyList() // Handle other categories as needed
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
            ){
                LazyColumn {
                    item {
                        LazyRow(Modifier.padding(bottom = 16.dp)) {
                            items(Utils.productionCategory) { category: Category ->
                                CategoryRowItem(
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
                                EggsListItem(
                                    eggCollection = collection as EggCollection,
                                    onItemClick = { onNavigate.invoke(collection.id) }
                                )
                            }
                            Utils.productionCategory[1] -> {
                                MilkListItem(
                                    milkCollection = collection as MilkCollection,
                                    onItemClick = { onNavigate.invoke(collection.id) }
                                )
                            }
                            Utils.productionCategory[2] -> {
                                FruitsListItem(
                                    fruitCollection = collection as FruitCollectionEntity,
                                    onItemClick = { onNavigate.invoke(collection.id) }
                                )
                            }
                            // Handle other categories as needed
                        }
                    }
                }


                if (isSyncing) {
                    ProgressIndicatorWidget()
                }
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



