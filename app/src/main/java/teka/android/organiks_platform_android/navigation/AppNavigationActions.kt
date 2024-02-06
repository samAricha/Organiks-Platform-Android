package teka.android.organiks_platform_android.navigation

import androidx.navigation.NavHostController

class AppNavigationActions(
    private val navController: NavHostController
) {

    fun navigateToHome() {
        navController.navigate(Screen.DashboardScreen.route) {
            popUpTo(Screen.Home.route)
        }
    }

    fun navigateToGeminichatScreen() {
        navController.navigate(Screen.GeminiChatScreen.route) {
            popUpTo(Screen.GeminiChatScreen.route)
        }
    }

    fun navigateToProductsHome() {
        navController.navigate(Screen.ProductionHome.route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun logoutNav() {
        navController.navigate(AUTH_GRAPH_ROUTE) {
            popUpTo(Screen.Login.route)
        }
    }

}