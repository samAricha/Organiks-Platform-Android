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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import teka.android.organiks_platform_android.modules.auth.AuthViewModel
import teka.android.organiks_platform_android.modules.auth.UserState
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.repository.DataStoreRepository
import teka.android.organiks_platform_android.ui.theme.OrganiksPlatformAndroidTheme
import teka.android.organiks_platform_android.util.components.SetBarColor
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private val userState by viewModels<AuthViewModel>()


    @RequiresApi(Build.VERSION_CODES.O)
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
            val imeiState = rememberImeState()

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            splashViewModel.startDestination.value?.let { Log.d("TAG3", it) }

            CompositionLocalProvider(UserState provides userState) {
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
