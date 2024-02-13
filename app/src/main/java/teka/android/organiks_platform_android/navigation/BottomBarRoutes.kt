package teka.android.organiks_platform_android.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import teka.android.organiks_platform_android.R

enum class BottomBarRoutes(
    val id: Int,
    @StringRes val title: Int,
    val routes: String,
    @DrawableRes val icon: Int
) {
    DASHBOARD(1, R.string.dashboard, Screen.DashboardScreen.route, R.drawable.home),
    PRODUCTION_HOME(
        2,
        R.string.productionHome, Screen.ProductionHome.route, R.drawable.monitoring
    ),
    PRODUCTION_RECORDING(3, R.string.productionRecording, Screen.ProductionRecording.route, R.drawable.add_to_list)
}