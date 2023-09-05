package teka.android.organiks_platform_android.navigation

const val ROOT_GRAPH_ROUTE = "root_graph_route"
const val AUTH_GRAPH_ROUTE = "auth_graph_route"
const val MAIN_GRAPH_ROUTE = "main_graph_route"
const val To_MAIN_GRAPH_ROUTE = "to_main_graph_route"


sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object Home : Screen(route = "home_screen")
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object ProductionHome: Screen(route = "production_home")
    object ProductionRecording: Screen(route = "production_recording")
    object DashboardScreen: Screen(route = "dashboard_screen")
    object AiSearchScreen: Screen(route = "ai_search_screen")
}