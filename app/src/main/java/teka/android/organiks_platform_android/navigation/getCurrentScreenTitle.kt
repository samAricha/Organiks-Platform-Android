package teka.android.organiks_platform_android.navigation

import androidx.compose.runtime.Composable

@Composable
fun getCurrentScreenTitle(currentRoute: String?): String {
    return when (currentRoute) {
        AppScreens.Welcome.route -> AppScreens.Welcome.title
        AppScreens.Home.route -> AppScreens.Home.title
        AppScreens.Login.route -> AppScreens.Login.title
        AppScreens.Registration.route -> AppScreens.Registration.title
        AppScreens.ProductionHome.route -> AppScreens.ProductionHome.title
        AppScreens.ProductionRecording.route -> AppScreens.ProductionRecording.title
        AppScreens.DashboardAppScreens.route -> AppScreens.DashboardAppScreens.title
        AppScreens.GeminiChatAppScreens.route -> AppScreens.GeminiChatAppScreens.title
        AppScreens.GeminiAnalystAppScreens.route -> AppScreens.GeminiAnalystAppScreens.title
        AppScreens.ProfileAppScreens.route -> AppScreens.ProfileAppScreens.title
        else -> "Organiks"
    }
}
