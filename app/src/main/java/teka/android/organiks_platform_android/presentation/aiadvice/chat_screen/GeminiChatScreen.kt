package teka.android.organiks_platform_android.presentation.aiadvice.chat_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.presentation.aiadvice.chat_screen.components.MessageBubble
import teka.android.organiks_platform_android.presentation.aiadvice.chat_screen.components.MessageImagesStack
import teka.android.organiks_platform_android.domain.models.ChatMessageModel
import teka.android.organiks_platform_android.domain.models.ChatStatusModel
import teka.android.organiks_platform_android.presentation.aiadvice.AiAdviceviewModel
import teka.android.organiks_platform_android.ui.theme.LightGreen
import teka.android.organiks_platform_android.ui.theme.LightRed

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GeminiChatScreen() {
    val viewModel: ChatViewModel = hiltViewModel();

    val chatUiState = viewModel.uiState
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val apiKeySnackBarHostState = remember { SnackbarHostState() }
    val errorSnackBarHostState = remember { SnackbarHostState() }
    val showDialog = remember { mutableStateOf(false) }


    Scaffold(
//        topBar = {
//            CustomAppBar(onActionClick = { showDialog.value = true })
//        },
        bottomBar = {
            CustomBottomSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 5.dp),
                status = chatUiState.value.status,
                onSendClick = { text, images ->
                    coroutineScope.launch(Dispatchers.IO) {
//                        viewModel.generateContent(text)
                        viewModel.generateGeminiResponse(text)
                    }
                },
                chatViewModel = viewModel
            )
        },
        snackbarHost = {
            SnackbarHost(errorSnackBarHostState) { data ->
                CustomSnackBar(
                    data = data,
                    containerColor = LightRed
                )
            }
            SnackbarHost(apiKeySnackBarHostState) { data ->
                CustomSnackBar(
                    data = data,
                    containerColor = LightGreen
                )
            }
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
            .padding(bottom = 15.dp)
    ) {
        ChatList(
            messages = chatUiState.value.messages
        )
        Spacer(modifier = Modifier.height(15.dp))

        if (showDialog.value) {
            CustomChatDialog(
                value = chatUiState.value.apiKey,
                onVisibilityChanged = { showDialog.value = it },
                onSaveClicked = {
                    coroutineScope.launch {
                        viewModel.setApiKey(it)
                        apiKeySnackBarHostState.currentSnackbarData?.dismiss()
                        if(chatUiState.value.status is ChatStatusModel.Success){
                            apiKeySnackBarHostState.showSnackbar(
                                message = (chatUiState.value.status as ChatStatusModel.Success).data,
                                withDismissAction = true
                            )
                        }

                    }
                }
            )
        }

//        errorSnackBarHostState.showSnackbar(chatUiState.value.status)
    }
}


@Composable
fun ChatList(messages: List<ChatMessageModel>) {
    val listState = rememberLazyListState()

    if (messages.isNotEmpty()) {
        LaunchedEffect(messages) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(messages.size) {
            val message = messages[it]
            if (message.images.isNotEmpty()) {
                MessageImagesStack(message = message)
                Spacer(modifier = Modifier.height(4.dp))
            }
            MessageBubble(message = message)
        }
    }
}