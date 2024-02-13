package teka.android.organiks_platform_android.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import teka.android.organiks_platform_android.MainAppScreen
import teka.android.organiks_platform_android.presentation.aiadvice.AiAdviceScreen
import teka.android.organiks_platform_android.presentation.aiadvice.chat_screen.GeminiChatScreen
import teka.android.organiks_platform_android.presentation.dashborad.DashboardScreen
import teka.android.organiks_platform_android.presentation.records.production.productionHome.ProductionHomeScreen
import teka.android.organiks_platform_android.presentation.records.production.productionRecording.ProductionRecordingScreen
import teka.android.organiks_platform_android.presentation.settings.SettingsScreen
import teka.android.organiks_platform_android.ui.animations.scaleIntoContainer
import teka.android.organiks_platform_android.ui.animations.scaleOutOfContainer


@Composable
fun MainNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.DashboardScreen.route,
        route = MAIN_GRAPH_ROUTE
    ) {

        composable(
            route = Screen.ProductionHome.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            ProductionHomeScreen(onNavigate = { id ->
                navController.navigate(route = "${Screen.ProductionRecording.route}?id=$id")
            })
        }

        composable(
            route = "${ Screen.ProductionRecording.route }?id={id}",
            arguments = listOf(navArgument("id"){type = NavType.IntType}),
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            val id = it.arguments?.getInt("id") ?: -1

            ProductionRecordingScreen(
                id = id,
                navController = navController,
            )
        }


        composable(
            route = Screen.DashboardScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

            ){
            DashboardScreen()
        }
        composable(
            route = Screen.GeminiChatScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            GeminiChatScreen()
        }
        composable(
            route = Screen.AiSearchScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

            ){
            AiAdviceScreen()
        }
        composable(
            route = Screen.ProfileScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
            ){
            SettingsScreen()
        }

    }
}