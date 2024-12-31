package teka.android.organiks_platform_android.util.helpers

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import teka.android.organiks_platform_android.navigation.AppScreens
import timber.log.Timber

@Composable
fun HandleBackPress(
    navHostController: NavHostController,
    fallbackRoute: String
) {
    BackHandler  {
        Timber.tag("BackHandler").i("BackHandler clicked")
        val isPopped = navHostController.popBackStack()
        if (!isPopped) {
            navHostController.navigate(fallbackRoute) {
                popUpTo(navHostController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }

    }
}
