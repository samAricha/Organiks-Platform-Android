package teka.android.organiks_platform_android.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object Home : Screen(route = "home_screen")
    object Login: Screen(route = "login_screen")
    object ProductionHome: Screen(route = "production_home")
    object ProductionRecording: Screen(route = "production_recording")
    object DashboardScreen: Screen(route = "dashboard_screen")
}