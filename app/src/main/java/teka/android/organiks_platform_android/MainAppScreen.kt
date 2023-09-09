package teka.android.organiks_platform_android

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.ui.theme.PlaceholderColor
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant
import teka.android.organiks_platform_android.ui.theme.ReemKufiMedium

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen() {
    val navHostController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "Organiks",
                        fontFamily = ReemKufiMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Screen.ProfileScreen.route)
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.Gray,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Profile",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
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
                            painter = painterResource(R.drawable.home),
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