package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.AppBar
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.NavigationDrawerM3
import teka.android.organiks_platform_android.ui.theme.PrimaryColor


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen(appState: AppState) {
    NavigationDrawerM3(appState)
}







































@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldContent(
     navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
     onDrawerIconClick: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    //this is where we call our drawer navigation from
                    onDrawerIconClick
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
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
                    selected = currentRoute == AppScreens.DashboardAppScreens.route,
                    onClick = {
                        navHostController.navigate(AppScreens.DashboardAppScreens.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(if (currentRoute == AppScreens.DashboardAppScreens.route) R.drawable.home else R.drawable.outline_home_24),
                            contentDescription = "Home",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == AppScreens.DashboardAppScreens.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            fontSize = 10.sp,
                            color = if (currentRoute == AppScreens.DashboardAppScreens.route) PrimaryColor else Color.Gray
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute == AppScreens.ProductionHome.route,
                    onClick = {
                        navHostController.navigate(AppScreens.ProductionHome.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.monitoring),
                            contentDescription = "Records",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == AppScreens.ProductionHome.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Records",
                            fontSize = 10.sp,
                            color = if (currentRoute == AppScreens.ProductionHome.route) PrimaryColor else Color.Gray
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute?.startsWith(AppScreens.ProductionRecording.route) == true,
                    onClick = {
                        navHostController.navigate(route = "${AppScreens.ProductionRecording.route}?id=-1")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.add_to_list),
                            contentDescription = "Add Record",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute?.startsWith(AppScreens.ProductionRecording.route) == true) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Add",
                            fontSize = 10.sp,
                            color = if (currentRoute?.startsWith(AppScreens.ProductionRecording.route) == true) PrimaryColor else Color.Gray
                        )
                    }
                )


                BottomNavigationItem(
                    selected = currentRoute?.startsWith(AppScreens.AiSearchAppScreens.route) == true,
                    onClick = {
                        navHostController.navigate(route = AppScreens.AiSearchAppScreens.route)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable._search_24),
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == AppScreens.AiSearchAppScreens.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Search",
                            fontSize = 10.sp,
                            color = if (currentRoute == AppScreens.AiSearchAppScreens.route) PrimaryColor else Color.Gray
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
        route = AppScreens.ProductionHome.route,
        icon = R.drawable.monitoring,
        contentDescription = "Send SMS",
        label = "Send SMS"
    ),
    BottomNavigationItem(
        route = AppScreens.ProductionHome.route,
        icon = R.drawable.monitoring,
        contentDescription = "Add Contact List",
        label = "Recipients"
    ),
    BottomNavigationItem(
        route = AppScreens.ProductionHome.route,
        icon = R.drawable.monitoring,
        contentDescription = "Contact List",
        label = "Other Screen"
    )
)