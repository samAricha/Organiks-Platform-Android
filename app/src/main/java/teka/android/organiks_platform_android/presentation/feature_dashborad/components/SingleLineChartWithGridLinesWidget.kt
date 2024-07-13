package teka.android.organiks_platform_android.presentation.feature_dashborad.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.presentation.feature_dashborad.utils.getLineChartDataFromEggCollection
import teka.android.organiks_platform_android.util.components.ProgressIndicatorWidget

@Composable
fun SingleLineChartWithGridLinesWidget(
    eggCollectionResults: List<EggCollectionResult>,
    isLoading: Boolean
) {

    if (eggCollectionResults.isNotEmpty() && !isLoading){

        val pointsData = getLineChartDataFromEggCollection(eggCollectionResults)

        val steps = 5
        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .topPadding(105.dp)
            .steps(pointsData.size - 1)
            .labelData { i -> pointsData[i].x.toInt().toString() }
            .labelAndAxisLinePadding(15.dp)
            .build()
        val yAxisData = AxisData.Builder()
            .steps(steps)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                // Add yMin to get the negative axis values to the scale
                val yMin = pointsData.minOf { it.y }
                val yMax = pointsData.maxOf { it.y }
                val yScale = (yMax - yMin) / steps
                ((i * yScale) + yMin).formatToSinglePrecision()
            }.build()
        val data = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                )
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines()
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            lineChartData = data
        )

    }else{
        ProgressIndicatorWidget()
    }


}
