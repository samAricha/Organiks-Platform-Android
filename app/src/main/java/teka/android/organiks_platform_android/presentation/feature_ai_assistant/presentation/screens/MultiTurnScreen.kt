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
import com.teka.geminichatsdk.spacee_gemini.components.ConversationArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.SelectedImageArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.GeminiAIViewModel
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.TypingArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ImageHelper

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultiTurnScreen() {

    val viewModel: GeminiAIViewModel = hiltViewModel();


    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val apiTypeState = remember { mutableStateOf(ApiType.DOCUMENT_CHAT) }



    val bitmaps: SnapshotStateList<Bitmap> = remember {
        mutableStateListOf()
    }


    LaunchedEffect(bitmaps.size) {
        println("BITMAPS CHANGED1: ${apiTypeState.value} ${bitmaps.isNotEmpty()}")
        if (bitmaps.isNotEmpty()) {
            apiTypeState.value = ApiType.IMAGE_CHAT
        } else {
            apiTypeState.value = ApiType.DOCUMENT_CHAT
        }
        println("BITMAPS CHANGED2: ${apiTypeState.value}")
    }

    val imageRequestBuilder = ImageRequest.Builder(context)
    val imageLoader = ImageLoader.Builder(context).build()



    //our various launchers
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        if (it != null) {
            bitmaps.add(it)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
    ) {
        it.forEach { uri ->
            coroutineScope.launch {
                ImageHelper.scaleDownBitmap(uri, imageRequestBuilder, imageLoader)?.let { bitmap ->
                    bitmaps.add(bitmap)
                }
            }
        }
    }

    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
    ) {
        it.forEach { uri ->
            coroutineScope.launch {
//                ImageHelper.scaleDownBitmap(uri, imageRequestBuilder, imageLoader)?.let { bitmap ->
//                    bitmaps.add(bitmap)
//                }
            }
        }
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                ConversationArea(
                    viewModel = viewModel,
                    apiType = apiTypeState.value
                )
            }
            SelectedImageArea(bitmaps = bitmaps)
            TypingArea(
                viewModel = viewModel,
                apiType = apiTypeState.value,
                bitmaps = bitmaps,
                galleryLauncher = galleryLauncher,
                documentLauncher = documentLauncher,
                permissionLauncher = permissionLauncher
            )
        }
    }

}
