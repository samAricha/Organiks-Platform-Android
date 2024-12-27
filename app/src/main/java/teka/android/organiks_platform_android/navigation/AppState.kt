package teka.android.organiks_platform_android.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navHostController: NavHostController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) =   remember(scaffoldState, navHostController, coroutineScope, drawerState) {
    AppState(scaffoldState, navHostController, coroutineScope, drawerState)
}

@Stable
class AppState(
    val scaffoldState: ScaffoldState,
    val navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    private val bottomBarRoutes = BottomBarRoutes.entries.map { it.routes }


    // StateFlow for shouldShowBottomBar with default value of true
    private val _shouldShowBottomBar = MutableStateFlow(true)
    val shouldShowBottomBar: StateFlow<Boolean> get() = _shouldShowBottomBar

    private val _currentRoute = MutableStateFlow<String>("Organiks")
    val currentRoute: StateFlow<String> get() = _currentRoute


    @Composable
    fun ObserveNavigationState() {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        LaunchedEffect(navBackStackEntry) {
            _shouldShowBottomBar.value = navBackStackEntry?.destination?.route in bottomBarRoutes
            _currentRoute.value = navBackStackEntry?.destination?.route.toString()
        }
    }

}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}