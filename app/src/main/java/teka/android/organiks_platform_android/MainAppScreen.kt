package teka.android.organiks_platform_android

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.navigation.*
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.AppBar
import teka.android.organiks_platform_android.presentation.feature_nav_drawer.NavigationDrawerM3
import teka.android.organiks_platform_android.ui.theme.PrimaryColor


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScreen(appState: AppState) {
    NavigationDrawerM3(appState)
}