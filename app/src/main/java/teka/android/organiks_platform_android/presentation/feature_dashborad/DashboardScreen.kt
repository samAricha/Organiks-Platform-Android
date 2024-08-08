package teka.android.organiks_platform_android.presentation.feature_dashborad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.presentation.feature_dashborad.components.BarchartWithSolidBarsWidget
import teka.android.organiks_platform_android.presentation.feature_dashborad.components.DashboardCard
import teka.android.organiks_platform_android.presentation.feature_dashborad.components.PiechartWithSliceLablesWidget
import teka.android.organiks_platform_android.presentation.feature_dashborad.components.SingleLineChartWithGridLinesWidget
import teka.android.organiks_platform_android.ui.theme.OrangeEnd
import teka.android.organiks_platform_android.ui.theme.OrangeStart
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant

@Composable
fun DashboardScreen(
    navController: NavController
) {

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

    val remoteEggCollectionList by viewModel.remoteEggCollections.collectAsState()


    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()


    LaunchedEffect(viewModel) {
        viewModel.viewModelInitialization()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Dashboard",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h6
            )
            LazyColumn {
                item {
                    LazyRow {
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
                                title = "Fruit Collection",
                                value = "$totalFruitsCollected Kgs",
                                iconResId = teka.android.organiks_platform_android.R.drawable.ic_fruits,
                                color = OrangeEnd
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
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Stats",
                            fontSize = 22.sp,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = "(Egg Collection Data)",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp),
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                val eggCollectionResults = listOf(
                    EggCollectionResult("uuid1", "20", "2", 1, 1627885440),
                    EggCollectionResult("uuid2", "15", "1", 2, 1627971840),
                    // Add more data here
                )

                item {
                    Text(
                        text = "Bar Chart",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp),
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BarchartWithSolidBarsWidget(
                            remoteEggCollectionList,
                            isLoading
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "Pie Chart",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp),
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PiechartWithSliceLablesWidget(
                            context,
                            eggCollectionResults,
                            isLoading
                        )
                    }
                }
                item {
                    Text(
                        text = "Line Chart",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp),
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SingleLineChartWithGridLinesWidget(
                            remoteEggCollectionList,
                            isLoading
                        )
                    }
                }

            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(AppScreens.GeminiAnalystAppScreens.route)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.gemini_transparent),
                contentDescription = "Add"
            )
        }

    }

}



