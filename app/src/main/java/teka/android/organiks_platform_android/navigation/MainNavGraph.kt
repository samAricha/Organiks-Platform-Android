package teka.android.organiks_platform_android.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens.MultiTurnScreen
import teka.android.organiks_platform_android.presentation.aiadvice.AiAdviceScreen
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens.GeminiAnalystScreen
import teka.android.organiks_platform_android.presentation.feature_dashborad.DashboardScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.profile.ProfileScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInViewModel
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionHome.ProductionHomeScreen
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingScreen
import teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.RemoteRecordsScreen
import teka.android.organiks_platform_android.presentation.feature_settings.SettingsScreen
import teka.android.organiks_platform_android.ui.animations.scaleIntoContainer
import teka.android.organiks_platform_android.ui.animations.scaleOutOfContainer


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = AppScreens.DashboardAppScreens.route,
        route = MAIN_GRAPH_ROUTE
    ) {

        composable(
            route = AppScreens.ProductionHome.route,
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
                navController.navigate(route = "${AppScreens.ProductionRecording.route}?id=$id")
            })
        }

        composable(
            route = AppScreens.RemoteRecordsScreens.route,
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
            RemoteRecordsScreen(onNavigate = { id ->
                navController.navigate(
                    route = "${AppScreens.ProductionRecording.route}?id=$id"
                )
            })
        }

        composable(
            route = "${ AppScreens.ProductionRecording.route }?id={id}",
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
            route = AppScreens.DashboardAppScreens.route,
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
            route = AppScreens.GeminiChatAppScreens.route,
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
//            GeminiChatScreen()
            MultiTurnScreen()
        }

        composable(
            route = AppScreens.GeminiAnalystAppScreens.route,
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
            GeminiAnalystScreen()
        }
        composable(
            route = AppScreens.AiSearchAppScreens.route,
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
            route = AppScreens.ProfileAppScreens.route,
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