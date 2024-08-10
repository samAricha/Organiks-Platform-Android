package teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.request.ImageRequest
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.ConversationArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.SelectedImageArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels.GeminiAIViewModel
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.TypingArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ImageHelper
import teka.android.organiks_platform_android.ui.theme.PlaceholderColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.quicksand

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultiTurnScreen() {

    val viewModel: GeminiAIViewModel = hiltViewModel();

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
//    val apiTypeState = remember { mutableStateOf(ApiType.DOCUMENT_CHAT) }
    val apiTypeState = remember { mutableStateOf(ApiType.MULTI_CHAT) }

    val languageOptions = viewModel.languageOptionItems
    val selectedLanguage by viewModel.selectedLanguageOption.collectAsState()


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


    var expanded by remember { mutableStateOf(false) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        modifier = Modifier
                            .width(120.dp)
                            .height(30.dp)
                            .background(PlaceholderColor, Shapes.large)
                            .clickable { expanded = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                        Text(
                            text = selectedLanguage,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .align(Alignment.CenterVertically),
                            fontWeight = FontWeight.ExtraLight,
                            fontFamily = quicksand
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 1.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        languageOptions.forEach { item ->
                            DropdownMenuItem(onClick = {
                                viewModel.updateSelectedLanguageOption(item.name)
                                expanded = false
                            }) {
                                Text(
                                    item.name,
                                    fontWeight = FontWeight.ExtraLight,
                                    fontFamily = quicksand
                                )
                            }
                        }
                    }
                }
            }

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
