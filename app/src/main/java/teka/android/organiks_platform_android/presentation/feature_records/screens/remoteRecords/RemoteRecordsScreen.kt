package teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import androidx.compose.foundation.Canvas
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.components.CategoryRowItem
import teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.components.EggsListItem
import teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.components.FruitsListItem
import teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.components.MilkListItem
import teka.android.organiks_platform_android.ui.theme.BackgroundColor
import teka.android.organiks_platform_android.ui.theme.DecentBlue
import teka.android.organiks_platform_android.util.CustomContextProvider
import teka.android.organiks_platform_android.util.components.LoadingAnimation
import teka.android.organiks_platform_android.util.components.ProgressIndicatorWidget


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RemoteRecordsScreen(
    onNavigate:(Int) -> Unit,
    navController: NavController
){
    var selectedCategory by remember { mutableStateOf(Utils.productionCategory[0]) }
    val remoteRecordsViewModel : RemoteRecordsViewModel = hiltViewModel()
    val eggCollections by remoteRecordsViewModel.eggCollections.collectAsState()


    val isSyncing by remoteRecordsViewModel.isSyncing.collectAsState()
    val fabClicked = remember { mutableStateOf(false) }

    val isLoading by remoteRecordsViewModel.isLoading.collectAsState()
    val errorMessage by remoteRecordsViewModel.errorMessage.collectAsState()
    val successMessage by remoteRecordsViewModel.successMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val snackbarData by remoteRecordsViewModel.snackbarData.collectAsState()

    if (snackbarData != null) {
        LaunchedEffect(snackbarData) {
            snackbarHostState.showSnackbar(snackbarData!!.message)
            remoteRecordsViewModel.clearSnackbar()
        }
    }

    val contextProvider = CustomContextProvider()
    val context = contextProvider.getContext()
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            val toast = Toast.makeText(context, "$it \u274C", Toast.LENGTH_LONG)
            toast.show()
        }
    }
    LaunchedEffect(successMessage) {
        successMessage?.let {
            val toast = Toast.makeText(context,  "$it \u2714", Toast.LENGTH_LONG)
            toast.show()
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
        else -> emptyList()
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = AppScreens.GeminiAnalystAppScreens.createRoute(
                        farmerDataId = selectedCategory.id+1,
                        autoGenerate = true
                    ))
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(54.dp),
                containerColor = BackgroundColor,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.gemini_transparent),
                    contentDescription = "Add",
                    tint = DecentBlue,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
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
                                    eggCollection = collection as EggCollectionResult,
                                    onItemClick = { onNavigate.invoke(collection.uuid.toInt()) }
                                )
                            }
                            Utils.productionCategory[1] -> {
                                MilkListItem(
                                    milkCollection = collection as MilkCollectionResult,
                                    onItemClick = { onNavigate.invoke(collection.uuid.toInt()) }
                                )
                            }
                            Utils.productionCategory[2] -> {
                                FruitsListItem(
                                    fruitCollection = collection as FruitCollectionDto,
                                    onItemClick = { onNavigate.invoke(collection.uuid.toInt()) }
                                )
                            }
                        }
                    }
                }

                if (isLoading) {
                    LoadingAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        circleSize = 16.dp,
                    )
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



