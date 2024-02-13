package teka.android.organiks_platform_android.presentation.dashborad

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import teka.android.organiks_platform_android.ui.theme.LightGreen
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant
import teka.android.organiks_platform_android.ui.theme.SecondaryColor

@Composable
fun DashboardScreen() {
    val viewModel : DashboardViewModel = hiltViewModel()

//    val totalEggs by rememberUpdatedState(newValue = viewModel.totalEggsCollected)
//    val totalMilk by rememberUpdatedState(newValue = viewModel.totalMilkCollected)

    val eggs by viewModel.eggCollections.collectAsState()
    val totalEggsCollected = eggs.sumOf { it.qty.toInt() }
    val totalEggsCracked = eggs.sumOf { it.cracked.toInt() }

    val milk by viewModel.milkCollections.collectAsState()
    val totalMilkCollected = milk.sumOf { it.qty.toDouble() }

    val totalNotBackedUpCount by viewModel.totalNotBackedUpCount.collectAsState()

    val context = LocalContext.current




    LaunchedEffect(viewModel) {
        viewModel.viewModelInitialization()
    }

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
            // sample data
            val pieChartData = listOf(
                PieData(value = 130F, label = "HTC", color = SecondaryColor, labelColor = Color.White),
                PieData(value = 260F, label = "Apple", color = LightGreen, labelColor = Color.White),
                PieData(value = 500F, label = "Google", color = PrimaryColor, labelColor = Color.White),
            )


            item {
                LazyRow{
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
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }


            item {
                ChartSection(
                    pieChartData1 = pieChartData,
                    pieChartData2 = pieChartData,
                    context = context
                )

            }
        }
    }
}

@Composable
fun ChartSection(
    pieChartData1: List<PieData>,
    pieChartData2: List<PieData>,
    context: Context
) {
    Text(
        text = "Chart Section",
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 8.dp),
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 5.dp)) {

            // Place the PieCharts in a Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // First PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),
                        data = pieChartData1,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Second PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),
//                            .size(220.dp),
                        data = pieChartData2,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // First PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),

                        data = pieChartData1,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Second PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),
//                            .size(220.dp),
                        data = pieChartData2,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun DashboardCard(
    title: String,
    value: String,
    iconResId: Int, // Resource ID for the icon drawable
    color: Color
) {
    // Load the drawable resource and convert it to a Painter
    val iconPainter = painterResource(id = iconResId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = iconPainter, // Use the loaded drawable
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = title,
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = PoppinsLight
            )
            Text(
                text = value,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = PoppinsLight
            )
        }
    }
}


