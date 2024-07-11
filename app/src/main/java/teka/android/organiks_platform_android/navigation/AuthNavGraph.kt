package teka.android.organiks_platform_android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import teka.android.organiks_platform_android.presentation.feature_auth.login.LoginScreen
import teka.android.organiks_platform_android.presentation.feature_auth.registration.RegisterScreen
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.WelcomeScreen
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.WelcomeViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){

    navigation(
        startDestination = AppScreens.Welcome.route,
        route = AUTH_GRAPH_ROUTE
    ){

        composable(route = AppScreens.Welcome.route) {
            val welcomeViewModel: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(
                navController = navController,
                welcomeViewModel = welcomeViewModel,
               )
        }


        composable(
            route = AppScreens.Login.route
        ) {
            LoginScreen(
                navController = navController
            )
        }

        composable(
            route = AppScreens.Registration.route
        ) {
            RegisterScreen(
                navController = navController,
            )
        }

    }
}