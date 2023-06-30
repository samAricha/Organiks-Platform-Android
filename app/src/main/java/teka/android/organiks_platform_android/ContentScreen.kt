package teka.android.organiks_platform_android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import teka.android.organiks_platform_android.navigation.OrganiksAndroidNavigation

@Composable
fun ContentScreen(navHostController: NavHostController, startDestination: String){
    OrganiksAndroidNavigation(
        navHostController = navHostController,
        startDestination = startDestination
    )

}