package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.ui.theme.OrganiksPlatformAndroidTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var navController: NavHostController

        Log.d("TAG1", "WORKIN")

//        installSplashScreen().setKeepOnScreenCondition {
//            Log.d("TAG1", splashViewModel.isLoading.value.toString())
//
//            !splashViewModel.isLoading.value
//        }

        setContent {
            Log.d("TAG3", splashViewModel.startDestination.value)
//            val startDestination by splashViewModel.startDestination


            OrganiksPlatformAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    navController = rememberNavController()
                    RootNavGraph()
//                    SetupNavGraph(navController = navController, startDestination = To_HOME_GRAPH_ROUTE)
//                    MainAppScreen(navHostController = navHostController, startDestination = startDestination)
//                    MainAppScreen(navHostController = navHostController, startDestination = HOME_GRAPH_ROUTE)
                }
            }
        }
    }
}
