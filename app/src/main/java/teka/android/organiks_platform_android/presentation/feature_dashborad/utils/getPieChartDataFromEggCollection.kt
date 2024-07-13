package teka.android.organiks_platform_android.presentation.feature_dashborad.utils

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import kotlin.random.Random

fun getPieChartDataFromEggCollection(
    results: List<EggCollectionResult>
): PieChartData {
    val slices = results.groupBy { it.eggTypeId }
        .map { (eggTypeId, eggCollectionResults) ->
            val totalQuantity = eggCollectionResults.fold(0f) { acc, result ->
                acc + result.quantity.toFloat()
            }
            // Creating a slice for each egg type
            PieChartData.Slice(
                label = "EggType$eggTypeId",
                value = totalQuantity,
                color = Color(
                    Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
                )
            )
        }
    return PieChartData(slices = slices, plotType = PlotType.Pie)
}
