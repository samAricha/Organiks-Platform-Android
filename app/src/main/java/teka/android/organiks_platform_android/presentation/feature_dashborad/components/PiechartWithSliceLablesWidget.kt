package teka.android.organiks_platform_android.presentation.feature_dashborad.components

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.presentation.feature_dashborad.utils.getPieChartDataFromEggCollection

@Composable
fun PiechartWithSliceLablesWidget(
    context: Context,
    eggCollectionResults: List<EggCollectionResult>
) {
    val pieChartData = getPieChartDataFromEggCollection(eggCollectionResults)

    val pieChartConfig = PieChartConfig(
        activeSliceAlpha = .9f,
        isEllipsizeEnabled = true,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
        sliceLabelTypeface = Typeface.defaultFromStyle(Typeface.ITALIC),
        isAnimationEnable = true,
        chartPadding = 20,
        showSliceLabels = true,
        labelVisible = true
    )

    Column(modifier = Modifier.height(500.dp)) {
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData, 3))
        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            pieChartData,
            pieChartConfig
        ) { slice ->
            Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
        }
    }
}

