package teka.android.organiks_platform_android.presentation.feature_nav_drawer

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.VoiceChat
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppNavigationActions
import teka.android.organiks_platform_android.navigation.AppState
import teka.android.organiks_platform_android.navigation.AppScreens
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NavigationDrawerM3(
    appState: AppState
) {
    val navHostController: NavHostController = rememberNavController()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val authViewModel: AuthViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val appState = rememberAppState(navHostController = navHostController)


    val navigationActions = remember(appState.navHostController) {
        AppNavigationActions(appState.navHostController)
    }

    val showDialog =  remember { mutableStateOf(false) }

    val items = listOf(
        DrawerItem(
            icon = Icons.Default.Home,
            label = "Home",
            secondaryLabel = "64",
            route = AppScreens.DashboardAppScreens.route,
            onItemClick = {
                navigationActions.navigateToHome()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "This is a Home Toast. Yay!", Toast.LENGTH_SHORT).show()
//                navHostController.navigate(Screen.Home.route)
            }
        ),
        DrawerItem(
            icon = Icons.Default.Notifications,
            label = "Notifications",
            secondaryLabel = "12",
            route = null,
            onItemClick = {
                Toast.makeText(context, "This is a Notifications Toast. Yay!", Toast.LENGTH_SHORT).show()
//                navHostController.navigate(Screen.ProductionHome.route)
            }
        ),
        DrawerItem(
            icon = Icons.Default.VoiceChat,
            label = "AiAssistant",
            secondaryLabel = "Chat",
            route = AppScreens.GeminiChatAppScreens.route,
            onItemClick = {
                navigationActions.navigateToGeminichatScreen()
                scope.launch {
                    drawerState.close()
                }
                Toast.makeText(context, "AiAssistant", Toast.LENGTH_SHORT).show()
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
            icon = Icons.Default.ExitToApp,
            label = "Log Out",
            secondaryLabel = "",
            route = null,
            onItemClick = {
                scope.launch {
                    drawerState.close()
                }
                showDialog.value = true
                Toast.makeText(context, "This is a Log Out Toast. Yay!", Toast.LENGTH_SHORT).show()
//               authViewModel.logout()
            }
        ),
    )
    val selectedItem by remember { mutableStateOf(items[0]) }




    if(showDialog.value)
        CustomDialog(value = "", setShowDialog = {
            showDialog.value = it
        }) {
            Log.i("HomePage","HomePage : $it")
        }



    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp),
                drawerContainerColor = Color.White,
                drawerShape = NoShapes.small
                ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Adjust the height to your preference
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(PrimaryColor, SecondaryColor),
                                startY = 0f,
                                endY = 200f // Adjust the endY to match the height
                            ),
                            shape = RoundedCornerShape(bottomEnd = 16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.egg100), // Replace with your image resource
                            contentDescription = "organiks mascot",
                            modifier = Modifier.size(100.dp) // Adjust the size of the image
                        )
                        Text(
                            text = "Organiks",
                            fontFamily = ReemKufiMedium,
                            color = Color.White,
                            fontSize = 25.sp
                        )
                    }
                }
                Spacer(Modifier.size(6.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.label, fontFamily = ReemKufi)
                                },
                        selected = item.route == currentRoute,
                        onClick = item.onItemClick,
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label)},
//                        badge = { Text(text = item.secondaryLabel)},
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = PrimaryLight,
                            unselectedContainerColor = Color.White
                        )

                    )
                }
            }
        },
        content = {
            ScaffoldContent(
                navHostController = appState.navHostController,
                scaffoldState = scaffoldState,
                scope = scope,
                onDrawerIconClick = { scope.launch { drawerState.open() } },
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





