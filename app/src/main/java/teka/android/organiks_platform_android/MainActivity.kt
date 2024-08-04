package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.presentation.feature_auth.UserState
import teka.android.organiks_platform_android.domain.repository.DataStoreRepository
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.profile.ProfileScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.GoogleAuthUiClient
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInViewModel
import teka.android.organiks_platform_android.ui.theme.OrganiksPlatformAndroidTheme
import teka.android.organiks_platform_android.util.components.SetBarColor

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private val authViewModel by viewModels<AuthViewModel>()




    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        // Create an instance of the ViewModel manually
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        splashViewModel.init(DataStoreRepository(context = applicationContext))
//        val startDestination by splashViewModel.startDestination
        splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }
//        splashScreen.setKeepOnScreenCondition{ startDestination.isNullOrEmpty() }


        //firebase auth screens
//        setContent {
//            CompositionLocalProvider(
//                UserState provides authViewModel
//            ) {
//                OrganiksPlatformAndroidTheme {
//                    Surface(
//                        modifier = Modifier.fillMaxSize(),
//                        //            color = colors.background
//                    ) {
//                        val navController = rememberNavController()
//
//
//                        // State to keep track of whether navigation is performed
//                        var isNavigationDone by remember { mutableStateOf(false) }
//
//                        // LaunchedEffect to handle initial navigation based on auth status
//                        LaunchedEffect(key1 = Unit) {
//                            // Check if the user is signed in
//                            val isSignedIn = authViewModel.googleAuthUiClient.getSignedInUser() != null
//                            // Navigate based on auth status if not done yet
//                            if (!isNavigationDone) {
//                                if (isSignedIn) {
//                                    navController.navigate("profile") {
//                                        // Clear back stack to prevent returning to sign-in
//                                        popUpTo("sign_in") { inclusive = true }
//                                    }
//                                } else {
//                                    navController.navigate("sign_in") {
//                                        // Clear back stack to prevent returning to profile
//                                        popUpTo("profile") { inclusive = true }
//                                    }
//                                }
//                                isNavigationDone = true
//                            }
//                        }
//
//                        NavHost(
//                            navController = navController,
//                            startDestination = "sign_in"
//                        ) {
//
//                            composable("sign_in") {
//                                val authViewModel: AuthViewModel = UserState.current
//
//                                val viewModel = viewModel<SignInViewModel>()
//                                val state by viewModel.state.collectAsStateWithLifecycle()
//
//                                LaunchedEffect(key1 = Unit) {
//                                    if (authViewModel.googleAuthUiClient.getSignedInUser() != null) {
//                                        navController.navigate("profile")
//                                    }
//                                }
//
//                                val launcher = rememberLauncherForActivityResult(
//                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
//                                    onResult = { result ->
//                                        if (result.resultCode == RESULT_OK) {
//                                            lifecycleScope.launch {
//                                                val signInResult =
//                                                    authViewModel.googleAuthUiClient.signInWithIntent(
//                                                        intent = result.data ?: return@launch
//                                                    )
//                                                viewModel.onSignInResult(signInResult)
//                                            }
//                                        }
//                                    }
//                                )
//
//                                LaunchedEffect(key1 = state.isSignInSuccessful) {
//                                    if (state.isSignInSuccessful) {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "Sign in successful",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//
//                                        navController.navigate("profile")
//                                        viewModel.resetState()
//                                    }
//                                }
//
//                                SignInScreen(
//                                    state = state,
//                                    onSignInClick = {
//                                        lifecycleScope.launch {
//                                            val signInIntentSender = authViewModel.googleAuthUiClient.signIn()
//                                            launcher.launch(
//                                                IntentSenderRequest.Builder(
//                                                    signInIntentSender ?: return@launch
//                                                ).build()
//                                            )
//                                        }
//                                    }
//                                )
//                            }
//
//
//                            composable("profile") {
//                                ProfileScreen(
//                                    userData = authViewModel.googleAuthUiClient.getSignedInUser(),
//                                    onSignOut = {
//                                        lifecycleScope.launch {
//                                            authViewModel.googleAuthUiClient.signOut()
//                                            Toast.makeText(
//                                                applicationContext,
//                                                "Signed out",
//                                                Toast.LENGTH_LONG
//                                            ).show()
//
//                                            navController.popBackStack()
//                                        }
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }












        setContent {
//            val imeiState = rememberImeState()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }

            var startDestination by remember { mutableStateOf<String?>(null) }

            // Check authentication status and determine the start destination
            LaunchedEffect(key1 = Unit) {
                val user = authViewModel.googleAuthUiClient.getSignedInUser()
                // Determine the start destination based on the user's authentication status
//                startDestination = if (user != null) To_MAIN_GRAPH_ROUTE else AUTH_GRAPH_ROUTE
                startDestination = if (user != null) To_MAIN_GRAPH_ROUTE else AUTH_GRAPH_ROUTE
            }

            CompositionLocalProvider(
                UserState provides authViewModel
            ) {
                OrganiksPlatformAndroidTheme {
                    SetBarColor(color = MaterialTheme.colorScheme.background)

                    startDestination?.let {
                        RootNavGraph(
                            navController = rememberNavController(),
                            startDestination = it
                        )
                    }
                }
            }

        }
    }
}
