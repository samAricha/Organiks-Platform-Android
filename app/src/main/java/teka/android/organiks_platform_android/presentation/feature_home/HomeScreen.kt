package teka.android.organiks_platform_android.presentation.feature_home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController) {
    val homeScreenViewModel : HomeScreenViewModel = hiltViewModel()

//    val memberCountState = homeScreenViewModel.memberCount.collectAsState()
//    val chamaaAccountsCountState = homeScreenViewModel.chamaaAccountsCount.collectAsState()
    val context = LocalContext.current


//    val memberCountText = when (val result = memberCountState.value) {
////        is EntityCountResult.Success -> {
////            "${result.data}"
////        }
////        is EntityCountResult.Error -> {
////            "0"
////        }
//    }
//
//    val chamaaAccountsCountText = when (val result = chamaaAccountsCountState.value) {
////        is EntityCountResult.Success -> {
////            "${result.data}"
////        }
////        is EntityCountResult.Error -> {
////            "0"
////        }
//    }

    LaunchedEffect(homeScreenViewModel) {
        homeScreenViewModel.getMemberCount()
    }
    Scaffold(

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyColumn(content = {
                item {
                    FeaturedBox(
                        totalMembers = "memberCountText",
                        totalChamaaAccounts = "chamaaAccountsCountText"
                    )
                }
                item {
                    QuickAccessSection(navController = navController)
                }

            })
        }
    }
}




