package teka.android.organiks_platform_android.util.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.AppState
import teka.android.organiks_platform_android.navigation.MainNavGraph
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.AppBar
import teka.android.organiks_platform_android.ui.theme.PrimaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldContent(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    onDrawerIconClick: () -> Unit,
    appState: AppState
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 AppBar(onNavigationIconClick = onDrawerIconClick)
        },
        bottomBar = {
            if (appState.shouldShowBottomBar){
                BottomAppBar(
                        modifier = Modifier.height(52.dp),
                        backgroundColor = Color.White,
                        cutoutShape = CircleShape,
                        elevation = 22.dp
                ) {
                        val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

                        BottomNavigationItem(
                            selected = currentRoute == AppScreens.HomeScreen.route,
                            onClick = {
                                navHostController.navigate(AppScreens.HomeScreen.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(if (currentRoute == AppScreens.HomeScreen.route) R.drawable.home else R.drawable.outline_home_24),
                                    contentDescription = "Home",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (currentRoute == AppScreens.HomeScreen.route) PrimaryColor else Color.Gray
                                )
                            },
                            label = {
                                androidx.compose.material.Text(
                                    text = "Home",
                                    fontSize = 10.sp,
                                    color = if (currentRoute == AppScreens.HomeScreen.route) PrimaryColor else Color.Gray
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(108.dp))

                        BottomNavigationItem(
                            selected = currentRoute == AppScreens.ProductionHome.route,
                            onClick = {
                                navHostController.navigate(AppScreens.ProductionHome.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                androidx.compose.material.Icon(
                                    painter = painterResource(R.drawable.monitoring),
                                    contentDescription = "Records",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (currentRoute == AppScreens.ProductionHome.route) PrimaryColor else Color.Gray
                                )
                            },
                            label = {
                                androidx.compose.material.Text(
                                    text = "Records",
                                    fontSize = 10.sp,
                                    color = if (currentRoute == AppScreens.ProductionHome.route) PrimaryColor else Color.Gray
                                )
                            }
                        )
                    }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (appState.shouldShowBottomBar){
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        navHostController.navigate(route = "${AppScreens.ProductionRecording.route}?id=-1")
                    },
                    backgroundColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Record",
                        tint = PrimaryColor
                    )
                }
            }

        }
    ) {
        if (appState.shouldShowBottomBar){
            Box(modifier = Modifier.padding(bottom = 39.dp)) {
                MainNavGraph(appState.navHostController,)
            }
        }else{
            MainNavGraph(appState.navHostController,)
        }

    }
}