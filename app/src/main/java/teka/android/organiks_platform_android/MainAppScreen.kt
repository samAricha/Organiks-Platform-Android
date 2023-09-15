package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.navDrawer.AppBar
import teka.android.organiks_platform_android.presentation.navDrawer.DrawerBody
import teka.android.organiks_platform_android.presentation.navDrawer.DrawerHeader
import teka.android.organiks_platform_android.presentation.navDrawer.MenuItem
import teka.android.organiks_platform_android.ui.theme.PlaceholderColor
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant
import teka.android.organiks_platform_android.ui.theme.ReemKufiMedium

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen() {
    val navHostController: NavHostController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    ),
                    MenuItem(
                        id = "settings",
                        title = "Settings",
                        contentDescription = "Go to settings screen",
                        icon = Icons.Default.Settings
                    ),
                    MenuItem(
                        id = "help",
                        title = "Help",
                        contentDescription = "Get help",
                        icon = Icons.Default.Info
                    ),
                ),
                onItemClick = {
                    when(it.id){
                        "settings" -> navHostController.navigate(Screen.ProfileScreen.route)
                    }
                    println("Clicked on ${it.title}")
                }
            )
        },

        bottomBar = {
            BottomNavigation(
                modifier = Modifier.height(52.dp),
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                BottomNavigationItem(
                    selected = currentRoute == Screen.DashboardScreen.route,
                    onClick = {
                        navHostController.navigate(Screen.DashboardScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(if (currentRoute == Screen.DashboardScreen.route) R.drawable.home else R.drawable.outline_home_24),
                            contentDescription = "Home",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == Screen.DashboardScreen.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            fontSize = 10.sp,
                            color = if (currentRoute == Screen.DashboardScreen.route) PrimaryColor else Color.Gray
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute == Screen.ProductionHome.route,
                    onClick = {
                        navHostController.navigate(Screen.ProductionHome.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.monitoring),
                            contentDescription = "Records",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == Screen.ProductionHome.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Records",
                            fontSize = 10.sp,
                            color = if (currentRoute == Screen.ProductionHome.route) PrimaryColor else Color.Gray
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute?.startsWith(Screen.ProductionRecording.route) == true,
                    onClick = {
                        navHostController.navigate(route = "${Screen.ProductionRecording.route}?id=-1")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.add_to_list),
                            contentDescription = "Add Record",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute?.startsWith(Screen.ProductionRecording.route) == true) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Add",
                            fontSize = 10.sp,
                            color = if (currentRoute?.startsWith(Screen.ProductionRecording.route) == true) PrimaryColor else Color.Gray
                        )
                    }
                )


                BottomNavigationItem(
                    selected = currentRoute?.startsWith(Screen.AiSearchScreen.route) == true,
                    onClick = {
                        navHostController.navigate(route = Screen.AiSearchScreen.route)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable._search_24),
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == Screen.AiSearchScreen.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Search",
                            fontSize = 10.sp,
                            color = if (currentRoute == Screen.AiSearchScreen.route) PrimaryColor else Color.Gray
                        )
                    }
                )
            }
        }
        ) {
            Box(modifier = Modifier.padding(bottom = 60.dp)) {
                MainNavGraph(navController = navHostController)
            }
        }
}













//to be used in refactoring the code
data class BottomNavigationItem(
    val route: String,
    val icon: Int,
    val contentDescription: String,
    val label: String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = Screen.ProductionHome.route,
        icon = R.drawable.monitoring,
        contentDescription = "Send SMS",
        label = "Send SMS"
    ),
    BottomNavigationItem(
        route = Screen.ProductionHome.route,
        icon = R.drawable.monitoring,
        contentDescription = "Add Contact List",
        label = "Recipients"
    ),
    BottomNavigationItem(
        route = Screen.ProductionHome.route,
        icon = R.drawable.monitoring,
        contentDescription = "Contact List",
        label = "Other Screen"
    )
)