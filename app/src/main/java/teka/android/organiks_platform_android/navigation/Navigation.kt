package teka.android.organiks_platform_android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import teka.android.organiks_platform_android.modules.auth.login_screen.LoginScreen
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.modules.splash_screen.presentation.WelcomeScreen
import teka.android.organiks_platform_android.presentation.dashborad.DashboardScreen
import teka.android.organiks_platform_android.presentation.records.production.productionHome.ProductionHomeScreen
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecording
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingScreen
import javax.inject.Inject

enum class Routes{
    ProductionHome,
    ProductionRecording,
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun OrganiksAndroidNavigation(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String
){
    NavHost(navController = navHostController, startDestination = startDestination ){

        composable(route = Screen.ProductionHome.route){
            ProductionHomeScreen(onNavigate = { id ->
                navHostController.navigate(route = "${Routes.ProductionRecording.name}?id=$id")
            })
        }

        composable(route = "${ Routes.ProductionRecording.name }?id={id}",
        arguments = listOf(navArgument("id"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("id") ?: -1


            ProductionRecordingScreen(id = id){
                navHostController.navigateUp()
            }

        }

        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navHostController)
        }



        composable(route = Screen.DashboardScreen.route){
            DashboardScreen()
        }

    }

}