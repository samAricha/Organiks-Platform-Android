package teka.android.organiks_platform_android.core

import android.annotation.SuppressLint
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.runtime.Composable
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.NavigationDrawerM3


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen(appState: AppState) {
    NavigationDrawerM3(appState)
}