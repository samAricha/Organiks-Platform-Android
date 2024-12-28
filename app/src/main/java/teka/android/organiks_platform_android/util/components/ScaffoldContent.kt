package teka.android.organiks_platform_android.util.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.AppState
import teka.android.organiks_platform_android.navigation.MainNavGraph
import teka.android.organiks_platform_android.navigation.getCurrentScreenTitle
import teka.android.organiks_platform_android.presentation.syncing.SyncViewModel
import teka.android.organiks_platform_android.ui.theme.MainWhiteColor
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant
import teka.android.organiks_platform_android.util.CustomContextProvider
import teka.android.organiks_platform_android.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldContent(
    navHostController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    appState: AppState,
    viewModel: SyncViewModel = hiltViewModel()
) {

    appState.ObserveNavigationState()
    val currentRoute by appState.currentRoute.collectAsState()
    val screenTitle = getCurrentScreenTitle(currentRoute)
    val showBottomBar by appState.shouldShowBottomBar.collectAsState()
    val contextProvider = CustomContextProvider()
    val context = contextProvider.getContext()

    val syncState by viewModel.syncState.collectAsState()



    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = screenTitle,
                hasBackNavigation = !showBottomBar,
                onBackNavigationClick = {
                    navHostController.popBackStack()
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.syncAllDataAsync()
                            Toast.makeText(context, "Backing up data", Toast.LENGTH_LONG).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cloud),
                            contentDescription = "Cloud Backup",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                drawerState = drawerState,
                scope = scope,
            )
        },
        bottomBar = {
            if (showBottomBar){
                BottomAppBar(
                    modifier = Modifier.background(MainWhiteColor).height(52.dp),
                    containerColor = Color.White,
                    tonalElevation = 12.dp
                ) {
                    val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    NavigationBarItem(
                        modifier = Modifier.fillMaxWidth(),
                        selected = currentRoute == AppScreens.HomeScreen.route,
                        onClick = {
                            navHostController.navigate(AppScreens.HomeScreen.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "Home",
                                modifier = Modifier.size(20.dp),
                                tint = if (currentRoute == AppScreens.HomeScreen.route) PrimaryColor else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = "Home",
                                fontSize = 10.sp,
                                color = if (currentRoute == AppScreens.HomeScreen.route) PrimaryColor else Color.Gray
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White,
                            selectedIconColor = MainWhiteColor
                        ),

                    )

                    Spacer(modifier = Modifier.width(108.dp))

                    NavigationBarItem(
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
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White,
                            selectedIconColor = MainWhiteColor
                        ),
                    )



                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (showBottomBar){
                FloatingActionButton(
                    modifier = Modifier
                        .offset(y = 45.dp),
                    shape = CircleShape,
                    onClick = {
                        navHostController.navigate(route = "${AppScreens.ProductionRecording.route}?id=-1")
                    },
                    containerColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Record",
                        tint = PrimaryVariant
                    )
                }
            }

        }
    ) { padding ->

        Box(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )
        ) {
            MainNavGraph(appState.navHostController)

            when (syncState) {
                is Resource.Loading -> {
                    LoadingScreen(
                        logoResId = R.drawable.cloud
                    )
                }
                is Resource.Success -> {
                    Toast.makeText(context, "Sync Successful!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error: ${(syncState as Resource.Error).message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Idle -> {
//                    Toast.makeText(context, "Error: ${(syncState as Resource.Error).message}", Toast.LENGTH_SHORT).show()
//
//                    Text("Press the button to start syncing.")
                }
            }
        }


//        if (showBottomBar){
//            Box(modifier = Modifier.padding(top = 65.dp, bottom = 39.dp)) {
//                MainNavGraph(appState.navHostController,)
//            }
//        }else{
//            Box(modifier = Modifier.padding(top = 65.dp, bottom = 0.dp)) {
//                MainNavGraph(appState.navHostController,)
//            }
//        }

    }
}