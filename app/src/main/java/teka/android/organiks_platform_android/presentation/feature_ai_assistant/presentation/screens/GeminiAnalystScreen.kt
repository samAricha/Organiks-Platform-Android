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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.SelectedImageArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.AnalystConversationArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.components.AnalystTypingArea
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.DataCollectionOptionModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.LanguageOptionModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels.GeminiAnalystViewModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.ui.theme.PlaceholderColor
import teka.android.organiks_platform_android.ui.theme.Shapes

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GeminiAnalystScreen(
    farmerDataId:Int
) {

    val viewModel: GeminiAnalystViewModel = hiltViewModel();

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

    val languageOptionItems = listOf(
        LanguageOptionModel(1, "English"),
        LanguageOptionModel(2, "Swahili"),
        LanguageOptionModel(3, "French")
    )
    val farmerDataOptionItems = listOf(
        DataCollectionOptionModel(1, "Eggs Data"),
        DataCollectionOptionModel(2, "Milk Data"),
        DataCollectionOptionModel(3, "Fruit Data")
    )
    var selectedLanguageOptionItem by remember { mutableStateOf(languageOptionItems[0]) }
    var selectedFarmerDataOptionItem by remember { mutableStateOf(farmerDataOptionItems[0]) }

    var languageDropDownExpanded by remember { mutableStateOf(false) }
    var farmerDataDropDropDownExpanded by remember { mutableStateOf(false) }

    // Set initial farmer data option based on farmerDataId
    LaunchedEffect(farmerDataId) {
        val initialOption = farmerDataOptionItems.find { it.id == farmerDataId }
        if (initialOption != null) {
            selectedFarmerDataOptionItem = initialOption
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
                    MaterialTheme.colorScheme.background
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
                            .height(40.dp)
                            .background(PlaceholderColor, Shapes.large)
                            .clickable { farmerDataDropDropDownExpanded = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = selectedFarmerDataOptionItem.name,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .align(Alignment.CenterVertically),
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
                        farmerDataOptionItems.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.onFarmerDataOptionChange(item.name)
                                    selectedFarmerDataOptionItem = item
                                    farmerDataDropDropDownExpanded = false
                                }
                            ) {
                                Text(item.name)
                            }
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        modifier = Modifier
                            .width(120.dp)
                            .height(40.dp)
                            .background(PlaceholderColor, Shapes.large)
                            .clickable { languageDropDownExpanded = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = selectedLanguageOptionItem.name,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .align(Alignment.CenterVertically),
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
                        languageOptionItems.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.onLanguageOptionChange(item.name)
                                    selectedLanguageOptionItem = item
                                    languageDropDownExpanded = false
                                }
                            ) {
                                Text(item.name)
                            }
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
