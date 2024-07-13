package teka.android.organiks_platform_android.presentation.feature_dashborad.utils

import co.yml.charts.common.model.Point
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult

fun getLineChartDataFromEggCollection(
    results: List<EggCollectionResult>
): List<Point> {
    return results.mapIndexed { index, result ->
        Point(
            x = index.toFloat(),  // Use the index as the X value
            y = result.quantity.toFloat()  // Use the quantity as the Y value
        )
    }
}
