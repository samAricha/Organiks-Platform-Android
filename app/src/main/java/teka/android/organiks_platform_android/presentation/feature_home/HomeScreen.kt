package teka.android.organiks_platform_android.presentation.feature_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.presentation.feature_dashborad.DashboardCard
import teka.android.organiks_platform_android.presentation.feature_dashborad.DashboardViewModel
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant


@Composable
fun HomeScreen(
    navController: NavController
) {
//    val homeScreenViewModel : HomeScreenViewModel = hiltViewModel()


    val viewModel : DashboardViewModel = hiltViewModel()


    val eggs by viewModel.eggCollections.collectAsState()
    val totalEggsCollected = eggs.sumOf { it.qty.toInt() }
    val totalEggsCracked = eggs.sumOf { it.cracked.toInt() }

    val milk by viewModel.milkCollections.collectAsState()
    val totalMilkCollected = milk.sumOf { it.qty.toDouble() }

    val fruits by viewModel.fruitCollections.collectAsState()
    val totalFruitsCollected = fruits.sumOf { it.qty.toDouble() }

    val totalNotBackedUpCount by viewModel.totalNotBackedUpCount.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.viewModelInitialization()
    }


    val cards = listOf(
        DashboardCardData(
            title = "Egg Collections",
            value = "$totalEggsCollected Eggs",
            iconResId = R.drawable.ic_egg_collection,
            color = PrimaryVariant
        ),
        DashboardCardData(
            title = "Milk Collection",
            value = "$totalMilkCollected Litres",
            iconResId = R.drawable.ic_milk_can,
            color = Color.Gray
        ),
        DashboardCardData(
            title = "Not Backed up",
            value = "$totalNotBackedUpCount Records",
            iconResId = R.drawable.baseline_sync_problem_24,
            color = Color(0xFFE57373)
        )
    )

//    LaunchedEffect(homeScreenViewModel) {
//        homeScreenViewModel.getMemberCount()
//    }
    Scaffold(

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyColumn(content = {
                item {
                    FeaturedBox(
                        totalEggsCollected = "$totalEggsCollected Eggs",
                        totalMilkCollected = "$totalMilkCollected Litres",
                        totalFruitCollected = "$totalFruitsCollected Kgs"
                    )
                }
                item {
                    QuickAccessSection(navController = navController)
                }
                items(
                    cards.chunked(2)
                ) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 5.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { cardData ->
                            DashboardCard(
                                title = cardData.title,
                                value = cardData.value,
                                iconResId = cardData.iconResId,
                                color = cardData.color,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            })
        }
    }
}




