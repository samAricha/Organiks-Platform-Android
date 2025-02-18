package teka.android.organiks_platform_android.navigation

import android.net.Uri

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
    data object FruitRecordingForm: AppScreens(route = "fruit_recording", title = "Record Fruit")
    data object CreateInvoiceScreen: AppScreens(route = "invoice_screen/{fruitCollectionId}", title = "Invoice"){
        fun createRoute(fruitCollectionId: String): String {
            return "invoice_screen/$fruitCollectionId"
        }
    }
    data object WeighingFormScreen: AppScreens(route = "weighing_screen/{fruitCollectionId}", title = "Invoice"){
        fun createRoute(fruitCollectionId: String): String {
            return "weighing_screen/$fruitCollectionId"
        }
    }
    data object PDFViewerScreen: AppScreens(route = "pdf_viewer_screen/{filePath}", title = "PDF Viewer"){
        fun createRoute(filePath: String): String {
            return "pdf_viewer_screen/${Uri.encode(filePath)}"
        }
    }
    data object InvoiceListScreen: AppScreens(route = "invoice_list_screen", title = "Invoices")
    data object AddCustomerForm: AppScreens(route = "add_customer_form", title = "Add Customer")
    data object FruitListScreen: AppScreens(route = "fruit_list_screen", title = "Fruit List")
    data object CustomerListScreen: AppScreens(route = "customer_list_screen", title = "Customer List")
    data object DashboardAppScreens: AppScreens(route = "dashboard_screen", title = "Analytics")
    data object RemoteRecordsScreens: AppScreens(route = "remote_records_screen", title = "RemoteRecords")
    data object GeminiChatAppScreens: AppScreens(route = "gemini_chat_screen", title = "Gemini Assistant")
    data object GeminiAnalystAppScreens: AppScreens(
        route = "gemini_analyst_screen/{farmerDataId}?autoGenerate={autoGenerate}",
        title = "Gemini Analyst"
    ){
        fun createRoute(farmerDataId: Int, autoGenerate: Boolean = false) =
            "gemini_analyst_screen/$farmerDataId?autoGenerate=$autoGenerate"
    }
    data object ProfileAppScreens: AppScreens(route = "profile_screen", title = "Profile")
    data object FirebaseProfileAppScreens: AppScreens(route = "firebase_profile_screen", title = "Firebase Profile")
    data object FirebaseSignInAppScreens: AppScreens(route = "firebase_signin_screen", title = "Firebase SignIn")
    data object HomeScreen : AppScreens(route = "app_home_screen", title = "Home Screen")
    data object FarmManagementModule : AppScreens(route = "farm_management_module_screen", title = "Farm Management Screen")
    data object CreateFarmScreen : AppScreens(route = "create_farm_screen", title = "Create Farm")
    data object MyFarmScreen : AppScreens(route = "my_farm_screen", title = "My Farm")
    data object ExpenditureForm : AppScreens(route = "expenditure_screen", title = "My Expenditure")
}