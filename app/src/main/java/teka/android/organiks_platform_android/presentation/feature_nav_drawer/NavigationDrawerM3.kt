package teka.android.organiks_platform_android.presentation.feature_nav_drawer

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.VoiceChat
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppNavigationActions
import teka.android.organiks_platform_android.navigation.AppState
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.getCurrentScreenTitle
import teka.android.organiks_platform_android.navigation.rememberAppState
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.ui.theme.NoShapes
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryLight
import teka.android.organiks_platform_android.ui.theme.ReemKufi
import teka.android.organiks_platform_android.ui.theme.ReemKufiMedium
import teka.android.organiks_platform_android.ui.theme.SecondaryColor
import teka.android.organiks_platform_android.ui.widgets.CustomDialog
import teka.android.organiks_platform_android.util.components.ScaffoldContent
import timber.log.Timber

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NavigationDrawerM3() {
    val navHostController: NavHostController = rememberNavController()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val appState = rememberAppState(navHostController = navHostController)
    val currentRoute by appState.currentRoute.collectAsState()



    val navigationActions = remember(appState.navHostController) {
        AppNavigationActions(appState.navHostController)
    }

    val showDialog =  remember { mutableStateOf(false) }

    val items = listOf(
        DrawerItem(
            icon = Icons.Default.Home,
            label = "Home",
            secondaryLabel = "64",
            route = AppScreens.HomeScreen.route,
            onItemClick = {
                navigationActions.navigateToHome()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Home. Yay!", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.Dashboard,
            label = "Dashboard",
            secondaryLabel = "64",
            route = AppScreens.RemoteRecordsScreens.route,
            onItemClick = {
                navigationActions.navigateToRemoteRecordsScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Dashboard. Yay!", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.StackedLineChart,
            label = "Analytics",
            secondaryLabel = "2",
            route = AppScreens.DashboardAppScreens.route,
            onItemClick = {
                navigationActions.navigateToDashboard()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Records", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.RequestQuote,
            label = "Invoices",
            secondaryLabel = "2",
            route = AppScreens.InvoiceListScreen.route,
            onItemClick = {
                navigationActions.navigateToInvoiceListScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Invoices", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.Analytics,
            label = "Analyst",
            secondaryLabel = "Gemini Analysis",
            route = AppScreens.GeminiAnalystAppScreens.route,
            onItemClick = {
                navigationActions.navigateToGeminiAnalystScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Gemini Analyst", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.VoiceChat,
            label = "Assistant",
            secondaryLabel = "Chat",
            route = AppScreens.GeminiChatAppScreens.route,
            onItemClick = {
                navigationActions.navigateToGeminichatScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Gemini Assistant", Toast.LENGTH_SHORT).show()
            }
        ),
//        DrawerItem(
//            icon = Icons.Default.VideoLibrary,
//            label = "Videos",
//            secondaryLabel = "",
//            route = null,
//            onItemClick = {
//                Toast.makeText(context, "Videos Coming Soon!", Toast.LENGTH_SHORT).show()
//            }
//        ),
//        DrawerItem(
//            icon = Icons.Default.Notifications,
//            label = "Notifications",
//            secondaryLabel = "12",
//            route = null,
//            onItemClick = {
//                Toast.makeText(context, "This is a Notifications Toast. Yay!", Toast.LENGTH_SHORT).show()
////                navHostController.navigate(Screen.ProductionHome.route)
//            }
//        ),
        DrawerItem(
            icon = Icons.Default.PermIdentity,
            label = "Profile",
            secondaryLabel = "12",
            route = null,
            onItemClick = {
                navigationActions.navigateToFirebaseProfileScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "profile screen", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.ExitToApp,
            label = "Log Out",
            secondaryLabel = "",
            route = null,
            onItemClick = {
                scope.launch {
                    drawerState.close()
                }
                showDialog.value = true
                Toast.makeText(context, "This is a Log Out Toast. Yay!!", Toast.LENGTH_SHORT).show()
//               authViewModel.logout()
            }
        ),
    )


    if(showDialog.value)
        CustomDialog(
            value = "",
            setShowDialog = {
                showDialog.value = it
            }
        ) {
            Timber.tag("HomePage").i("HomePage : %s", it)
        }



    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            drawerSheet(
                scope = scope,
                drawerState = drawerState,
                context = context,
                navigationActions = navigationActions,
                showDialog = showDialog,
                currentRoute = currentRoute
            )
        },
        content = {
            ScaffoldContent(
                navHostController = appState.navHostController,
//                scaffoldState = scaffoldState,
                scope = scope,
                drawerState = drawerState,
//                onDrawerIconClick = { scope.launch { drawerState.open() } },
                appState = appState
            )
        }
    )
}

data class DrawerItem(
    val icon: ImageVector,
    val label: String,
    val route: String?,
    val secondaryLabel: String,
    val onItemClick: () -> Unit
)


@Composable
fun drawerSheet(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navigationActions: AppNavigationActions,
    context: Context,
    showDialog: MutableState<Boolean>,
    currentRoute: String
) {

    val items = listOf(
        DrawerItem(
            icon = Icons.Default.Home,
            label = "Home",
            secondaryLabel = "64",
            route = AppScreens.HomeScreen.route,
            onItemClick = {
                navigationActions.navigateToHome()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Home. Yay!", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.Dashboard,
            label = "Dashboard",
            secondaryLabel = "64",
            route = AppScreens.DashboardAppScreens.route,
            onItemClick = {
                navigationActions.navigateToDashboard()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Dashboard. Yay!", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.Receipt,
            label = "All Records",
            secondaryLabel = "Remote Records",
            route = AppScreens.RemoteRecordsScreens.route,
            onItemClick = {
                navigationActions.navigateToRemoteRecordsScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Records", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.VoiceChat,
            label = "Assistant",
            secondaryLabel = "Chat",
            route = AppScreens.GeminiChatAppScreens.route,
            onItemClick = {
                navigationActions.navigateToGeminichatScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Gemini Assistant", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.Analytics,
            label = "Analyst",
            secondaryLabel = "Gemini Analysis",
            route = AppScreens.GeminiAnalystAppScreens.route,
            onItemClick = {
                navigationActions.navigateToGeminiAnalystScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "Gemini Analyst", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.VideoLibrary,
            label = "Videos",
            secondaryLabel = "",
            route = null,
            onItemClick = {
                Toast.makeText(context, "Videos Coming Soon!", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.Notifications,
            label = "Notifications",
            secondaryLabel = "12",
            route = null,
            onItemClick = {
                Toast.makeText(context, "This is a Notifications Toast. Yay!", Toast.LENGTH_SHORT)
                    .show()
//                navHostController.navigate(Screen.ProductionHome.route)
            }
        ),
        DrawerItem(
            icon = Icons.Default.PermIdentity,
            label = "Profile",
            secondaryLabel = "12",
            route = null,
            onItemClick = {
                navigationActions.navigateToFirebaseProfileScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "profile screen", Toast.LENGTH_SHORT).show()
            }
        ),
        DrawerItem(
            icon = Icons.Default.ExitToApp,
            label = "Log Out",
            secondaryLabel = "",
            route = null,
            onItemClick = {
                scope.launch {
                    drawerState.close()
                }
                showDialog.value = true
                Toast.makeText(context, "This is a Log Out Toast. Yay!!", Toast.LENGTH_SHORT).show()
//               authViewModel.logout()
            }
        ),
    )
}








