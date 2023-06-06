package teka.android.organiks_platform_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import teka.android.organiks_platform_android.presentation.records.production.productionHome.ProductionHomeScreen
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecording
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingScreen

enum class Routes{
    ProductionHome,
    ProductionRecording,
}

@Composable
fun OrganiksAndroidNavigation(
    navHostController: NavHostController = rememberNavController()
){
    NavHost(navController = navHostController, startDestination = Routes.ProductionHome.name ){

        composable(route = Routes.ProductionHome.name){
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
    }

}