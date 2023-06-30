package teka.android.organiks_platform_android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import teka.android.organiks_platform_android.modules.splash_screen.presentation.WelcomeScreen
import teka.android.organiks_platform_android.presentation.dashborad.DashboardScreen
import teka.android.organiks_platform_android.presentation.records.production.productionHome.ProductionHomeScreen
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.ProductionHome.route ){

//        composable(route = Screen.ProductionHome.route){
//            ProductionHomeScreen(onNavigate = { id ->
//                navController.navigate(route = "${Routes.ProductionRecording.name}?id=$id")
//            })
//        }
//
//        composable(route = "${ Routes.ProductionRecording.name }?id={id}",
//            arguments = listOf(navArgument("id"){type = NavType.IntType})
//        ){
//            val id = it.arguments?.getInt("id") ?: -1
//
//
//            ProductionRecordingScreen(id = id){
//                navController.navigateUp()
//            }
//
//        }

        composable(route = Screen.DashboardScreen.route){
            DashboardScreen()
        }

    }
}