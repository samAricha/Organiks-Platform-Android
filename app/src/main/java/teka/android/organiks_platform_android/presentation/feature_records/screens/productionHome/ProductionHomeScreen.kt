package teka.android.organiks_platform_android.presentation.feature_records.screens.productionHome

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
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.ProgressIndicator
import teka.android.organiks_platform_android.presentation.feature_records.screens.CategoryItem


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductionHomeScreen(
    onNavigate:(Int) -> Unit,
    navController: NavController
){
    var selectedCategory by remember { mutableStateOf(Utils.productionCategory[0]) }
    val productionHomeViewModel : ProductionHomeViewModel = hiltViewModel()

    val isSyncing by productionHomeViewModel.isSyncing.collectAsState()
    val fabClicked by productionHomeViewModel.fabClicked.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val snackbarData by productionHomeViewModel.snackbarData.collectAsState()

    if (snackbarData != null) {
        LaunchedEffect(snackbarData) {
            scope.launch {
                val result = snackbarHostState
                    .showSnackbar(
                        message = snackbarData!!.message,
                        actionLabel = "Action",
                        duration = SnackbarDuration.Short
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        /* Handle snackbar action performed */
                    }
                    SnackbarResult.Dismissed -> {
                        /* Handle snackbar dismissed */
                    }
                }
            }

            productionHomeViewModel.clearSnackbar()
        }
    }

    val eggCollectionsState by productionHomeViewModel.eggCollections.collectAsState()
    val milkCollectionsState by productionHomeViewModel.milkCollections.collectAsState()
    val fruitCollectionsState by productionHomeViewModel.fruitCollections.collectAsState()


    val collections = when (selectedCategory) {
        Utils.productionCategory[0] -> eggCollectionsState
        Utils.productionCategory[1] -> milkCollectionsState
        Utils.productionCategory[2] -> fruitCollectionsState
        else -> emptyList() // Handle other categories as needed
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
        FloatingActionButton(
            onClick = {
                productionHomeViewModel.onFabClicked()
            },
            containerColor = PrimaryColor
        ) {
            Icon(painter = painterResource(R.drawable.cloud_upload),
                contentDescription = null,
            tint = Color.White
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
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp, bottom = 50.dp)
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                CategorySelection(
                    categories = Utils.productionCategory,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
                if (collections.isEmpty()) {
                    EmptyCollectionViewState(navController)
                } else {
                    LazyColumn {
                        items(collections) { collection ->
                            when (selectedCategory) {
                                Utils.productionCategory[0] -> {
                                    EggCollectionItem(
                                        eggCollection = collection as EggCollection,
                                        onItemClick = { onNavigate(collection.id) }
                                    )
                                }

                                Utils.productionCategory[1] -> {
                                    MilkCollectionItem(
                                        milkCollection = collection as MilkCollection,
                                        onItemClick = { onNavigate(collection.id) }
                                    )
                                }

                                Utils.productionCategory[2] -> {
                                    FruitCollectionItem(
                                        fruitCollection = collection as FruitCollectionEntity,
                                        onItemClick = { onNavigate(collection.id) }
                                    )
                                }
                            }
                        }
                    }

                    if (isSyncing) {
                        ProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun CategorySelection(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyRow(
        Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(categories) { category ->
            CategoryItem(
                iconRes = category.resId,
                title = category.title,
                selected = category == selectedCategory
            ) {
                onCategorySelected(category)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}



@Composable
fun CollectionItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    date: Long,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick.invoke() }
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(0.dp)) {
                Image(
                    painter = painterResource(iconRes),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = title, fontFamily = PoppinsLight)
                Text(text = subtitle, fontFamily = PoppinsLight)
            }
            Box(modifier = Modifier.padding(8.dp).fillMaxSize()) {
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
                Text(text = formattedDate, fontFamily = PoppinsExtraLight, modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}


@Composable
fun EggCollectionItem(
    eggCollection: EggCollection,
    onItemClick: () -> Unit
) {
    CollectionItem(
        iconRes = if (eggCollection.isBackedUp) R.drawable.checkmark else R.drawable.cloud_not_done,
        title = "Kienyeji",
        subtitle = "Total: ${eggCollection.qty} Eggs\nCracked: ${eggCollection.cracked} Eggs",
        date = eggCollection.date,
        onItemClick = onItemClick
    )
}

@Composable
fun MilkCollectionItem(
    milkCollection: MilkCollection,
    onItemClick: () -> Unit
) {
    CollectionItem(
        iconRes = if (milkCollection.isBackedUp) R.drawable.checkmark else R.drawable.cloud_not_done,
        title = "Milk",
        subtitle = "Qty: ${milkCollection.qty} litres",
        date = milkCollection.date,
        onItemClick = onItemClick
    )
}

@Composable
fun FruitCollectionItem(
    fruitCollection: FruitCollectionEntity,
    onItemClick: () -> Unit
) {
    CollectionItem(
        iconRes = if (fruitCollection.isBackedUp) R.drawable.checkmark else R.drawable.cloud_not_done,
        title = "Fruits",
        subtitle = "Qty: ${fruitCollection.qty} kg",
        date = fruitCollection.date,
        onItemClick = onItemClick
    )
}


@Composable
fun EmptyCollectionViewState(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.amazed100),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            text = "Add your first record.",
            textAlign = TextAlign.Center
        )
        FilledTonalButton(
            onClick = {
                navController.navigate(route = "${AppScreens.ProductionRecording.route}?id=-1")
            },
            colors = ButtonDefaults.filledTonalButtonColors(containerColor = PrimaryColor)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add record",
                    tint = Color.White
                )
                Text(text = "Add A Record", color = Color.White)
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

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryColor)
    }
}

