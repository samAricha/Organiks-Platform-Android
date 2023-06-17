package teka.android.organiks_platform_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.navigation.OrganiksAndroidNavigation
import teka.android.organiks_platform_android.ui.theme.OrganiksPlatformAndroidTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        Log.d("TAG1", "WORKIN")

        installSplashScreen().setKeepOnScreenCondition {
            Log.d("TAG1", splashViewModel.isLoading.value.toString())

            !splashViewModel.isLoading.value
        }

        setContent {
            Log.d("TAG3", splashViewModel.startDestination.value)
            var navHostController: NavHostController = rememberNavController()
            val startDestination by splashViewModel.startDestination
            OrganiksPlatformAndroidTheme {

                OrganiksAndroidNavigation(navHostController = navHostController, startDestination = startDestination)

            }
        }
    }
}


