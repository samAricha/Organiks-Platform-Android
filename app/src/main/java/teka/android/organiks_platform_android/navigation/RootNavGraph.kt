package teka.android.organiks_platform_android.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import teka.android.organiks_platform_android.MainAppScreen
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.ui.theme.PrimaryColor

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel
) {
    val appState = rememberAppState(navHostController = navController)


    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = ROOT_GRAPH_ROUTE
    ){

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