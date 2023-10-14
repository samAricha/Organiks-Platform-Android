package teka.android.organiks_platform_android.presentation.navDrawer

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.ScaffoldContent
import teka.android.organiks_platform_android.modules.auth.AuthViewModel
import teka.android.organiks_platform_android.navigation.AppNavigationActions
import teka.android.organiks_platform_android.navigation.MainNavGraph
import teka.android.organiks_platform_android.navigation.Screen
import teka.android.organiks_platform_android.navigation.To_MAIN_GRAPH_ROUTE
import teka.android.organiks_platform_android.ui.theme.LightPrimaryColor
import teka.android.organiks_platform_android.ui.theme.NoShapes
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryLight
import teka.android.organiks_platform_android.ui.theme.PrimaryVariant
import teka.android.organiks_platform_android.ui.theme.ReemKufiBold
import teka.android.organiks_platform_android.ui.theme.ReemKufiMedium
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.widgets.CustomDialog

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NavigationDrawerM3(
) {

    val navHostController: NavHostController = rememberNavController()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val authViewModel: AuthViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navigationActions = remember(navHostController) {
        AppNavigationActions(navHostController)
    }

    val showDialog =  remember { mutableStateOf(false) }


    val items = listOf(
        DrawerItem(
            icon = Icons.Default.Home,
            label = "Home",
            secondaryLabel = "64",
            onItemClick = {
                // Define the action for the "Home" item here
                // For example, navigate to the Home screen
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
            onItemClick = {
                // Define the action for the "Notifications" item here
                // For example, navigate to the Notifications screen
                Toast.makeText(context, "This is a Notifications Toast. Yay!", Toast.LENGTH_SHORT).show()
//                navHostController.navigate(Screen.ProductionHome.route)
            }
        ),
        DrawerItem(
            icon = Icons.Default.ExitToApp,
            label = "Log Out",
            secondaryLabel = "",
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
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = Color.White,
                drawerShape = NoShapes.small
                ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(PrimaryColor)
//                        .padding(vertical = 24.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "Organiks", fontFamily = ReemKufiBold, color = Color.White, fontSize = 45.sp)
//                }
                Spacer(Modifier.size(6.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.label) },
                        selected = item == selectedItem,
                        onClick = item.onItemClick,
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label)},
                        badge = { Text(text = item.secondaryLabel)},
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = PrimaryLight,
                            unselectedContainerColor = Color.White
                        )

                    )
                }
            }
        },
        content = {
            ScaffoldContent2(
                navHostController = navHostController,
                scaffoldState = scaffoldState,
                scope = scope,
                onDrawerIconClick = { scope.launch { drawerState.open() } }
            )
        }
    )
}

data class DrawerItem(
    val icon: ImageVector,
    val label: String,
    val secondaryLabel: String,
    val onItemClick: () -> Unit
)



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldContent2(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    onDrawerIconClick: () -> Unit
) {
    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 AppBar(onNavigationIconClick = onDrawerIconClick)
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
                        androidx.compose.material.Icon(
                            painter = painterResource(if (currentRoute == Screen.DashboardScreen.route) R.drawable.home else R.drawable.outline_home_24),
                            contentDescription = "Home",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == Screen.DashboardScreen.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        androidx.compose.material.Text(
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
                        androidx.compose.material.Icon(
                            painter = painterResource(R.drawable.monitoring),
                            contentDescription = "Records",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute == Screen.ProductionHome.route) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        androidx.compose.material.Text(
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
                        androidx.compose.material.Icon(
                            painter = painterResource(R.drawable.add_to_list),
                            contentDescription = "Add Record",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute?.startsWith(Screen.ProductionRecording.route) == true) PrimaryColor else Color.Gray
                        )
                    },
                    label = {
                        androidx.compose.material.Text(
                            text = "Add",
                            fontSize = 10.sp,
                            color = if (currentRoute?.startsWith(Screen.ProductionRecording.route) == true) PrimaryColor else Color.Gray
                        )
                    }
                )


//                BottomNavigationItem(
//                    selected = currentRoute?.startsWith(Screen.AiSearchScreen.route) == true,
//                    onClick = {
//                        navHostController.navigate(route = Screen.AiSearchScreen.route)
//                    },
//                    icon = {
//                        androidx.compose.material.Icon(
//                            painter = painterResource(R.drawable._search_24),
//                            contentDescription = "Search",
//                            modifier = Modifier.size(20.dp),
//                            tint = if (currentRoute == Screen.AiSearchScreen.route) PrimaryColor else Color.Gray
//                        )
//                    },
//                    label = {
//                        androidx.compose.material.Text(
//                            text = "Search",
//                            fontSize = 10.sp,
//                            color = if (currentRoute == Screen.AiSearchScreen.route) PrimaryColor else Color.Gray
//                        )
//                    }
//                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(bottom = 60.dp)) {
            MainNavGraph(navHostController)
        }
    }




}













@Composable
fun Content(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = ">>> Swipe >>>")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onClick) {
            Text(text = "Click to Open")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content2(
    onMenuIconClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onMenuIconClick) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
                    }
                },
                title = { Text(text = "Top Stories")}
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(50) {
                ListItem(
                    headlineContent = { Text(text = "Item $it")},
                    leadingContent = {
                        Icon(Icons.Default.Face, contentDescription = null)
                    }
                )
            }
        }
    }
}