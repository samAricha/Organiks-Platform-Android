package teka.android.organiks_platform_android.navigation

const val ROOT_GRAPH_ROUTE = "root_graph_route"
const val AUTH_GRAPH_ROUTE = "auth_graph_route"
const val MAIN_GRAPH_ROUTE = "main_graph_route"
const val To_MAIN_GRAPH_ROUTE = "to_main_graph_route"


sealed class AppScreens(
    val route: String,
    val title: String
) {
    data object Welcome : AppScreens(route = "welcome_screen", title = "Welcome")
    data object Home : AppScreens(route = "home_screen", title = "Home")
    data object Login: AppScreens(route = "login_screen", title = "Login")
    data object Registration: AppScreens(route = "registration_screen", title = "Registration")
    data object ProductionHome: AppScreens(route = "production_home", title = "Productions")
    data object ProductionRecording: AppScreens(route = "production_recording", title = "Recording")
    data object DashboardAppScreens: AppScreens(route = "dashboard_screen", title = "Dashboard")
    data object RemoteRecordsScreens: AppScreens(route = "remote_records_screen", title = "RemoteRecords")
    data object GeminiChatAppScreens: AppScreens(route = "gemini_chat_screen", title = "Gemini Assistant")
    data object GeminiAnalystAppScreens: AppScreens(
        route = "gemini_analyst_screen/{farmerDataId}",
        title = "Gemini Analyst"
    ){
        fun createRoute(farmerDataId: Int) = "gemini_analyst_screen/$farmerDataId"
    }
    data object ProfileAppScreens: AppScreens(route = "profile_screen", title = "Profile")
    data object FirebaseProfileAppScreens: AppScreens(route = "firebase_profile_screen", title = "Firebase Profile")
    data object FirebaseSignInAppScreens: AppScreens(route = "firebase_signin_screen", title = "Firebase SignIn")
    data object HomeScreen : AppScreens(route = "app_home_screen", title = "Home Screen")
}