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
    private val userState by viewModels<AuthViewModel>()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        // Create an instance of the ViewModel manually
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        splashViewModel.init(DataStoreRepository(context = applicationContext))
        val startDestination by splashViewModel.startDestination
        splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }
        splashScreen.setKeepOnScreenCondition{ startDestination.isNullOrEmpty() }
        setContent {

            OrganiksPlatformAndroidTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //            color = colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "sign_in"
                    ) {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("profile")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("profile")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }












//        setContent {
////            val imeiState = rememberImeState()
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }
//
//            CompositionLocalProvider(UserState provides userState) {
//                OrganiksPlatformAndroidTheme {
//                    SetBarColor(color = MaterialTheme.colorScheme.background)
//
//                    startDestination?.let {
//                        RootNavGraph(
//                            navController = rememberNavController(),
//                            startDestination = it
//                        )
//                    }
//                }
//            }
//
//        }
    }
}
