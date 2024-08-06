package teka.android.organiks_platform_android.presentation.feature_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
fun HomeScreen(navController: NavController) {
    val homeScreenViewModel : HomeScreenViewModel = hiltViewModel()


    val viewModel : DashboardViewModel = hiltViewModel()


//    val totalEggs by rememberUpdatedState(newValue = viewModel.totalEggsCollected)
//    val totalMilk by rememberUpdatedState(newValue = viewModel.totalMilkCollected)

    val eggs by viewModel.eggCollections.collectAsState()
    val totalEggsCollected = eggs.sumOf { it.qty.toInt() }
    val totalEggsCracked = eggs.sumOf { it.cracked.toInt() }

    val milk by viewModel.milkCollections.collectAsState()
    val totalMilkCollected = milk.sumOf { it.qty.toDouble() }

    val totalNotBackedUpCount by viewModel.totalNotBackedUpCount.collectAsState()

//    val memberCountState = homeScreenViewModel.memberCount.collectAsState()
//    val chamaaAccountsCountState = homeScreenViewModel.chamaaAccountsCount.collectAsState()
    val context = LocalContext.current


//    val memberCountText = when (val result = memberCountState.value) {
////        is EntityCountResult.Success -> {
////            "${result.data}"
////        }
////        is EntityCountResult.Error -> {
////            "0"
////        }
//    }
//
//    val chamaaAccountsCountText = when (val result = chamaaAccountsCountState.value) {
////        is EntityCountResult.Success -> {
////            "${result.data}"
////        }
////        is EntityCountResult.Error -> {
////            "0"
////        }
//    }


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

    LaunchedEffect(homeScreenViewModel) {
        homeScreenViewModel.getMemberCount()
    }
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
                        totalMembers = "memberCountText",
                        totalChamaaAccounts = "chamaaAccountsCountText"
                    )
                }
                item {
                    QuickAccessSection(navController = navController)
                }
                item {
                    LazyRow(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(horizontal = 5.dp)
                    ){
                        item {
                            DashboardCard(
                                title = "Egg Collections",
                                value = "$totalEggsCollected Eggs",
                                iconResId = teka.android.organiks_platform_android.R.drawable.ic_egg_collection,
                                color = PrimaryVariant
                            )
                        }
                        item {
                            DashboardCard(
                                title = "Milk Collection",
                                value = "$totalMilkCollected Litres",
                                iconResId = teka.android.organiks_platform_android.R.drawable.ic_milk_can,
                                color = Color.Gray
                            )
                        }
                        item {
                            DashboardCard(
                                title = "Not Backed up",
                                value = "$totalNotBackedUpCount Records",
                                iconResId = teka.android.organiks_platform_android.R.drawable.baseline_sync_problem_24,
                                color = Color(0xFFE57373)
                            )
                        }
                    }
                }

                items(
                    cards.chunked(2)
                ) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 8.dp,
                                horizontal = 5.dp
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




