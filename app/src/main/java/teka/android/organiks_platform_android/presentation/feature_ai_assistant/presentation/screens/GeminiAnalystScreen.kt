package teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.SelectedImageArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.AnalystConversationArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.AnalystTypingArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels.GeminiAnalystViewModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.ui.theme.PlaceholderColor
import teka.android.organiks_platform_android.ui.theme.Shapes
import teka.android.organiks_platform_android.ui.theme.quicksand

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GeminiAnalystScreen(
    farmerDataId:Int,
    autoGenerate: Boolean = false,
    viewModel: GeminiAnalystViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val apiTypeState = remember { mutableStateOf(ApiType.MULTI_CHAT) }
    val bitmaps: SnapshotStateList<Bitmap> = remember {
        mutableStateListOf()
    }

    val languageOptions = viewModel.languageOptionItems
    val farmerDataOptions = viewModel.farmerDataOptionItems
    val selectedLanguage by viewModel.selectedLanguageOption.collectAsState()
    val selectedFarmerData by viewModel.selectedFarmerDataOption.collectAsState()


    var languageDropDownExpanded by remember { mutableStateOf(false) }
    var farmerDataDropDropDownExpanded by remember { mutableStateOf(false) }


    // Set initial farmer data option based on farmerDataId
    LaunchedEffect(farmerDataId) {
        val initialOption = farmerDataOptions.find { it.id == farmerDataId }
        if (initialOption != null) {
            viewModel.updateSelectedFarmerDataOption(initialOption.name)
        }
    }


    // Fetch data based on autoGenerate and farmerDataId
    LaunchedEffect(autoGenerate) {
        if (autoGenerate) {
            viewModel.fetchDataBasedOnId(farmerDataId)
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
                .background(
                    Color.White
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {

                    Row(
                        modifier = Modifier
                            .width(140.dp)
                            .height(30.dp)
                            .background(PlaceholderColor, Shapes.large)
                            .clickable { farmerDataDropDropDownExpanded = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = selectedFarmerData,
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
                        expanded = farmerDataDropDropDownExpanded,
                        onDismissRequest = { farmerDataDropDropDownExpanded = false }
                    ) {
                        farmerDataOptions.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.updateSelectedFarmerDataOption(item.name)
                                    farmerDataDropDropDownExpanded = false
                                },
                                text = {
                                    Text(
                                        item.name,
                                        fontWeight = FontWeight.ExtraLight,
                                        fontFamily = quicksand
                                    )
                                }
                            )
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        modifier = Modifier
                            .width(120.dp)
                            .height(30.dp)
                            .background(PlaceholderColor, Shapes.large)
                            .clickable { languageDropDownExpanded = true },
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
                        expanded = languageDropDownExpanded,
                        onDismissRequest = { languageDropDownExpanded = false }
                    ) {
                        languageOptions.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.updateSelectedLanguageOption(item.name)
                                    languageDropDownExpanded = false
                                },
                                text = {
                                    Text(
                                        item.name,
                                        fontWeight = FontWeight.ExtraLight,
                                        fontFamily = quicksand
                                    )
                                }
                            )
                        }
                    }
                }
            }
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
