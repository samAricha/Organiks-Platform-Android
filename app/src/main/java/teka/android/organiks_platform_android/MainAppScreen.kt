package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.ui.theme.PrimaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen(navHostController: NavHostController = rememberNavController(),
                  startDestination: String) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = PrimaryColor,
                title = {
                    Text(
                        text = "Organiks",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                })
        },

        bottomBar = {
            BottomNavigation {
                val navController = navHostController
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                BottomNavigationItem(
                    selected = currentRoute == Screen.DashboardScreen.route,
                    onClick = {
                        navController.navigate(Screen.DashboardScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.home),
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(text = "Home")
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute == Screen.ProductionHome.route,
                    onClick = {
                        navController.navigate(Screen.ProductionHome.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.monitoring),
                            contentDescription = "Records"
                        )
                    },
                    label = {
                        Text(text = "Records")
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute?.startsWith(Routes.ProductionRecording.name) == true,
                    onClick = {
                        navHostController.navigate(route = "${Routes.ProductionRecording.name}?id=-1")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.add_to_list),
                            contentDescription = "Add Record"
                        )
                    },
                    label = {
                        Text(text = "Add Record")
                    }
                )
            }
        }

    ) {

//        ContentScreen(navHostController = navHostController,
//            startDestination = startDestination)

//        SetupNavGraph(navController = navHostController, startDestination = HOME_GRAPH_ROUTE)
        MainNavGraph(navController = navHostController, Screen.ProductionHome.route)


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