package teka.android.organiks_platform_android.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
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
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =   remember(scaffoldState, navHostController, coroutineScope) {
    AppState(scaffoldState, navHostController, coroutineScope)
}

@Stable
class AppState(
    val scaffoldState: ScaffoldState,
    val navHostController: NavHostController,
    coroutineScope: CoroutineScope
) {
    private val bottomBarRoutes = BottomBarRoutes.entries.map { it.routes }

//    val shouldShowBottomBar: Boolean
//        @Composable get() =
//            navHostController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    // StateFlow for shouldShowBottomBar with default value of true
    private val _shouldShowBottomBar = MutableStateFlow(true)
    val shouldShowBottomBar: StateFlow<Boolean> get() = _shouldShowBottomBar


    val currentRoute: String?
        get() = navHostController.currentDestination?.route

    @Composable
    fun ObserveNavigationState() {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        LaunchedEffect(navBackStackEntry) {
            _shouldShowBottomBar.value = navBackStackEntry?.destination?.route in bottomBarRoutes
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