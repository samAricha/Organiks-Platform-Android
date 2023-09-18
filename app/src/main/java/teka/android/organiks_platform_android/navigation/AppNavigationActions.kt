package teka.android.organiks_platform_android.navigation

import androidx.navigation.NavHostController

class AppNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(Screen.DashboardScreen.route) {
            popUpTo(Screen.Home.route)
        }
    }

    fun navigateToProductsHome() {
        navController.navigate(Screen.ProductionHome.route) {
            launchSingleTop = true
            restoreState = true
        }
    }

}