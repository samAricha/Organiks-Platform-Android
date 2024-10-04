package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.NavigationDrawerM3


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen() {
    NavigationDrawerM3()
}