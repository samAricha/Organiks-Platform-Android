package teka.android.organiks_platform_android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import teka.android.organiks_platform_android.modules.auth.login_screen.LoginScreen
import teka.android.organiks_platform_android.modules.splash_screen.presentation.WelcomeScreen
import teka.android.organiks_platform_android.presentation.dashborad.DashboardScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screen.Login.route,
        route = AUTH_GRAPH_ROUTE
    ){

        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(navController = navController)
        }

    }
}