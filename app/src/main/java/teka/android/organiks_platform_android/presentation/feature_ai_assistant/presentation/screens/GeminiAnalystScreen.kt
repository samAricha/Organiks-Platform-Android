package teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.request.ImageRequest
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.SelectedImageArea
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.AnalystConversationArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.AnalystTypingArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels.GeminiAnalystViewModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ImageHelper

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GeminiAnalystScreen() {

    val viewModel: GeminiAnalystViewModel = hiltViewModel();

    val remoteEggCollections by viewModel.eggCollections.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val apiTypeState = remember { mutableStateOf(ApiType.MULTI_CHAT) }



    val bitmaps: SnapshotStateList<Bitmap> = remember {
        mutableStateListOf()
    }


    LaunchedEffect(bitmaps.size) {
        println("BITMAPS CHANGED1: ${apiTypeState.value} ${bitmaps.isNotEmpty()}")
        if (bitmaps.isNotEmpty()) {
            apiTypeState.value = ApiType.IMAGE_CHAT
        } else {
//            apiTypeState.value = ApiType.DOCUMENT_CHAT
            apiTypeState.value = ApiType.MULTI_CHAT
        }
        println("BITMAPS CHANGED2: ${apiTypeState.value}")
    }


    Scaffold(
        topBar = {

        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .fillMaxHeight(1f)
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                AnalystConversationArea(
                    viewModel = viewModel,
                    apiType = apiTypeState.value
                )
            }
            SelectedImageArea(bitmaps = bitmaps)
            AnalystTypingArea(
                viewModel = viewModel,
                apiType = apiTypeState.value,
                bitmaps = bitmaps,
            )
        }
    }

}