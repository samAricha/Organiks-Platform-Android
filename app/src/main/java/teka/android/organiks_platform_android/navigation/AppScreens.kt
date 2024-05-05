package teka.android.organiks_platform_android.navigation

const val ROOT_GRAPH_ROUTE = "root_graph_route"
const val AUTH_GRAPH_ROUTE = "auth_graph_route"
const val MAIN_GRAPH_ROUTE = "main_graph_route"
const val To_MAIN_GRAPH_ROUTE = "to_main_graph_route"


sealed class AppScreens(val route: String) {
    object Welcome : AppScreens(route = "welcome_screen")
    object Home : AppScreens(route = "home_screen")
    object Login: AppScreens(route = "login_screen")
    object Registration: AppScreens(route = "registration_screen")
    object ProductionHome: AppScreens(route = "production_home")
    object ProductionRecording: AppScreens(route = "production_recording")
    object DashboardAppScreens: AppScreens(route = "dashboard_screen")
    object AiSearchAppScreens: AppScreens(route = "ai_search_screen")
    object GeminiChatAppScreens: AppScreens(route = "gemini_chat_screen")
    object ProfileAppScreens: AppScreens(route = "profile_screen")

    object Splash : AppScreens("splash")
    object BottomBar : AppScreens("bottombar")
    object Detail : AppScreens("detail")
}