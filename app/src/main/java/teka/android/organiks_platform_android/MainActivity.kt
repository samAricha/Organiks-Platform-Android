package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import teka.android.organiks_platform_android.modules.auth.AuthViewModel
import teka.android.organiks_platform_android.modules.auth.UserState
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.ui.theme.OrganiksPlatformAndroidTheme
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private val userState by viewModels<AuthViewModel>()


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAG1", "WORKINg")

        val isLoading = splashViewModel.isLoading.value
        installSplashScreen().setKeepOnScreenCondition {
            Log.d("TAG2", splashViewModel.isLoading.value.toString())

            !splashViewModel.isLoading.value
        }

        setContent {
            val imeiState = rememberImeState()

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }

            CompositionLocalProvider(UserState provides userState) {
                OrganiksPlatformAndroidTheme {
                    val startDestination by splashViewModel.startDestination
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
