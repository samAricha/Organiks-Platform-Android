package teka.android.organiks_platform_android.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import teka.android.organiks_platform_android.MainAppScreen
import teka.android.organiks_platform_android.modules.auth.UserState
import teka.android.organiks_platform_android.modules.auth.login.LoginScreen
import teka.android.organiks_platform_android.ui.theme.PrimaryColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val appState = rememberAppState(navHostController = navController)


    NavHost(navController = navController,
        startDestination = startDestination,
        route = ROOT_GRAPH_ROUTE){


        authNavGraph(navController = navController)

        composable(route = To_MAIN_GRAPH_ROUTE){
            MainAppScreen(appState)
        }

    }
}

@Composable
fun ProgressIndicator(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryColor)
    }
}