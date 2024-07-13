package teka.android.organiks_platform_android.presentation.feature_dashborad.utils

import androidx.compose.ui.graphics.Color
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import kotlin.random.Random

fun getBarChartDataFromEggCollection(
    results: List<EggCollectionResult>
): List<BarData> {
    return results.mapIndexed { index, result ->
        val point = Point(
            x = index.toFloat(),  // Use the index as the X value
            y = result.quantity.toFloat()  // Use the quantity as the Y value
        )
        BarData(
            point = point,
            color = Color(
                Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
            ),
            label = "EggType${result.eggTypeId}",
            dataCategoryOptions = DataCategoryOptions()
        )
    }
}
