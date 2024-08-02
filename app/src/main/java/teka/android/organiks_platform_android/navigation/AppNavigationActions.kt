package teka.android.organiks_platform_android.navigation

import androidx.navigation.NavHostController

class AppNavigationActions(
    private val navController: NavHostController
) {

    fun navigateToHome() {
        navController.navigate(AppScreens.DashboardAppScreens.route) {
            popUpTo(AppScreens.Home.route)
        }
    }

    fun navigateToGeminichatScreen() {
        navController.navigate(AppScreens.GeminiChatAppScreens.route) {
            popUpTo(AppScreens.GeminiChatAppScreens.route)
        }
    }

    fun navigateToGeminiAnalystScreen() {
        navController.navigate(AppScreens.GeminiAnalystAppScreens.route) {
            popUpTo(AppScreens.GeminiAnalystAppScreens.route)
        }
    }

    fun navigateToRemoteRecordsScreen() {
        navController.navigate(AppScreens.RemoteRecordsScreens.route) {
            popUpTo(AppScreens.RemoteRecordsScreens.route)
        }
    }

    fun navigateToProductsHome() {
        navController.navigate(AppScreens.ProductionHome.route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun logoutNav() {
        navController.navigate(AUTH_GRAPH_ROUTE) {
            popUpTo(AppScreens.Login.route)
        }
    }

}