package teka.android.organiks_platform_android.presentation.feature_dashborad.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarStyle
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.navigation.ProgressIndicator
import teka.android.organiks_platform_android.presentation.feature_dashborad.utils.getBarChartDataFromEggCollection

@Composable
fun BarchartWithSolidBarsWidget(
    eggCollectionResults: List<EggCollectionResult>,
    isLoading: Boolean
) {

    // Check if data is loaded and not empty
    if (eggCollectionResults.isNotEmpty() && !isLoading) {
        val maxRange = 50
        val barData = getBarChartDataFromEggCollection(eggCollectionResults)
        val yStepSize = 10

        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .steps(barData.size - 1)
            .bottomPadding(40.dp)
            .axisLabelAngle(20f)
            .startDrawPadding(48.dp)
            .labelData { index -> barData[index].label }
            .build()
        val yAxisData = AxisData.Builder()
            .steps(yStepSize)
            .labelAndAxisLinePadding(20.dp)
            .axisOffset(20.dp)
            .labelData { index -> (index * (maxRange / yStepSize)).toString() }
            .build()
        val barChartData = BarChartData(
            chartData = barData,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            barStyle = BarStyle(
                paddingBetweenBars = 20.dp,
                barWidth = 25.dp
            ),
            showYAxis = true,
            showXAxis = true,
            horizontalExtraSpace = 10.dp,
        )
        BarChart(
            modifier = Modifier.height(350.dp),
            barChartData = barChartData
        )
    } else {
        // Handle the loading state or empty state
        ProgressIndicator()
    }
}
