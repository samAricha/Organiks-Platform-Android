package teka.android.organiks_platform_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import teka.android.organiks_platform_android.MainAppScreen

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController,
        startDestination = AUTH_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE){

        authNavGraph(navController = navController)

        composable(To_MAIN_GRAPH_ROUTE){
            MainAppScreen(startDestination = Screen.ProductionHome.route)
        }

    }
}