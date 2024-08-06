package teka.android.organiks_platform_android.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.presentation.feature_auth.UserState
import teka.android.organiks_platform_android.presentation.feature_auth.login.LoginScreen
import teka.android.organiks_platform_android.presentation.feature_auth.registration.RegisterScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInViewModel
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.WelcomeScreen
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.WelcomeViewModel
import teka.android.organiks_platform_android.ui.animations.scaleIntoContainer
import teka.android.organiks_platform_android.ui.animations.scaleOutOfContainer

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
){

    navigation(
        startDestination = AppScreens.Welcome.route,
//        startDestination = AppScreens.FirebaseSignInAppScreens.route,
        route = AUTH_GRAPH_ROUTE
    ){


        composable(
            route = AppScreens.FirebaseSignInAppScreens.route,
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

        ) {
            SignInScreen(
                navController = navController,
            )
        }


        composable(
            route = AppScreens.Welcome.route,
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

        ) {
            val welcomeViewModel: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(
                navController = navController,
                welcomeViewModel = welcomeViewModel,
               )
        }


        composable(
            route = AppScreens.Login.route,
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

        ) {
            LoginScreen(
                navController = navController
            )
        }

        composable(
            route = AppScreens.Registration.route,
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
        ) {
            RegisterScreen(
                navController = navController,
            )
        }

    }
}