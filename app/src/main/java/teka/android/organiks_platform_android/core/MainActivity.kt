package teka.android.organiks_platform_android.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.presentation.feature_auth.UserState
import teka.android.organiks_platform_android.domain.repository.DataStoreRepository
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.FirebaseAuthViewModel
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInViewModel
import teka.android.organiks_platform_android.ui.theme.OrganiksPlatformAndroidTheme
import teka.android.organiks_platform_android.util.components.SetBarColor

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private val authViewModel by viewModels<AuthViewModel>()
    private val firebaseAuthViewModel by viewModels<FirebaseAuthViewModel>()





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


        setContent {
//            val imeiState = rememberImeState()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }

            var startDestination by remember { mutableStateOf<String?>(null) }



            //firebase and Google Authentication
//            val googleAuthUiClient by lazy {
//                GoogleAuthUiClient(
//                    context = applicationContext,
//                    oneTapClient = Identity.getSignInClient(applicationContext)
//                )
//            }

            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()





            CompositionLocalProvider(
                UserState provides authViewModel
            ) {
                OrganiksPlatformAndroidTheme {
                    SetBarColor(color = MaterialTheme.colorScheme.background)

                    val isUserSignedIn by authViewModel.isUserSignedIn.collectAsState()
                    val currentUser = firebaseAuthViewModel.currentUser.collectAsState().value

                    LaunchedEffect(currentUser) {
//                        startDestination = if (currentUser != null) To_MAIN_GRAPH_ROUTE else AUTH_GRAPH_ROUTE
                        startDestination = To_MAIN_GRAPH_ROUTE
                    }


                    startDestination?.let {
                        RootNavGraph(
                            navController = rememberNavController(),
                            startDestination = it,
                            authViewModel = authViewModel
                        )
                    }
                }
            }

        }
    }
}
