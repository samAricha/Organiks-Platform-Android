package teka.android.organiks_platform_android.presentation.feature_ai_assistant.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels.GeminiAnalystViewModel
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.PrimaryLight

@Composable
fun AnalystTypingArea(
    viewModel: GeminiAnalystViewModel,
    apiType: ApiType,
    bitmaps: SnapshotStateList<Bitmap>? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val remoteEggCollections by viewModel.eggCollections.collectAsState()
    val currentLanguage by viewModel.selectedLanguageOption.collectAsState()
    val selectedFarmData by viewModel.requestOptionData.collectAsState()



    val isGenerating: Boolean? = when (apiType) {
        ApiType.MULTI_CHAT -> viewModel.conversationList.observeAsState().value?.lastOrNull()?.isGenerating
        ApiType.SINGLE_CHAT -> TODO()
        ApiType.IMAGE_CHAT -> TODO()
        ApiType.DOCUMENT_CHAT -> TODO()
    }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 3.dp,
                end = 10.dp,
                start = 10.dp
            )
            .background(colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .background(colorScheme.background)
        ) {

            DropdownMenuItem(
                modifier = Modifier.background(colorScheme.background),
                onClick = {
                    expanded = false
                    viewModel.clearContext()
                },
                text = {
                    Text(
                        color = PrimaryColor,
                        text = "Refresh",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = R.drawable.refresh),
                        tint = PrimaryColor,
                        contentDescription = "refresh"
                    )
                }
            )
        }


           IconButton(
               onClick = { expanded = true }
           ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.add_icon),
                    tint = PrimaryColor,
                    contentDescription = "add"
                )
           }


        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            placeholder = {
                Text(
                    color = PrimaryLight,
                    text = "What's in your mind?"
                )
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(colorScheme.background),
            shape = RoundedCornerShape(28),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryColor,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = colorScheme.background,
                unfocusedContainerColor = colorScheme.background,
                cursorColor = PrimaryColor
            ),
            maxLines = 5,
            trailingIcon = {
                Box(
                    modifier = Modifier.padding(end = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isGenerating != true) {
                        Icon(
                            painter = painterResource(id = R.drawable.send_icon),
                            contentDescription = "send",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (
                                        text.text
                                            .trim()
                                            .isNotEmpty()
                                        && (apiType != ApiType.IMAGE_CHAT || bitmaps!!.isNotEmpty())
                                    ) {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                        when (apiType) {
                                            ApiType.SINGLE_CHAT -> TODO()

                                            ApiType.MULTI_CHAT -> viewModel.makeMultiTurnAnalyticalQuery(
                                                prompt =text.text.trim(),
                                                supportingText = "$selectedFarmData : please provide response in $currentLanguage Language",
                                            )

                                            ApiType.IMAGE_CHAT -> TODO()
                                            ApiType.DOCUMENT_CHAT -> TODO()
                                        }
                                        text = TextFieldValue("")
                                    }
                                },
                            tint = PrimaryColor
                        )
                    } else {
                        val strokeWidth = 2.dp
                        CircularProgressIndicator(
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        Color.Black,
                                        radius = size.width / 2 - strokeWidth.toPx() / 2,
                                        style = Stroke(strokeWidth.toPx())
                                    )
                                }
                                .size(30.dp),
                            color = Color.LightGray,
                            strokeWidth = strokeWidth
                        )
                    }
                }
            },
            textStyle = TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
            )
        )
    }
}