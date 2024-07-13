package teka.android.organiks_platform_android.presentation.feature_dashborad

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.utils.DataUtils
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.presentation.feature_dashborad.components.PiechartWithSliceLablesWidget
import teka.android.organiks_platform_android.ui.theme.PoppinsLight
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant
import teka.android.organiks_platform_android.util.components.PiechartWithSliceLables
import teka.android.organiks_platform_android.util.components.SingleLineChartWithGridLines

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

    val remoteEggCollectionList by viewModel.remoteEggCollections.collectAsState()





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
                        text = "(Demo Data)",
                        fontSize = 14.sp,
                        color = Color.Gray, // Or any other color you prefer
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
                    color = Color.Gray, // Or any other color you prefer
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
                teka.android.organiks_platform_android.presentation.feature_dashborad.components.BarchartWithSolidBars(
                    remoteEggCollectionList
                )

//                BarchartWithSolidBars()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    text = "Pie Chart",
                    fontSize = 14.sp,
                    color = Color.Gray, // Or any other color you prefer
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
//                PiechartWithSliceLables(context = context)
                PiechartWithSliceLablesWidget(
                    context,
                    eggCollectionResults
                )

            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    text = "Line Chart",
                    fontSize = 14.sp,
                    color = Color.Gray, // Or any other color you prefer
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
                SingleLineChartWithGridLines(
                    DataUtils.getLineChartData(
                        100,
                        start = 50,
                        maxRange = 100
                    )
                )
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


