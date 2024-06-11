package com.teka.geminichatsdk.spacee_gemini.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.modules.ai_assistant.components.MessageItem
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.modules.ai_assistant.data.Message
import teka.android.organiks_platform_android.modules.ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.modules.ai_assistant.presentation.GeminiAIViewModel

@Composable
fun ConversationArea(
    viewModel: GeminiAIViewModel,
    apiType: ApiType
) {
    val response: List<Message>? = when (apiType) {
        ApiType.MULTI_CHAT -> viewModel.conversationList.observeAsState().value?.toList()
        ApiType.SINGLE_CHAT -> TODO()
        ApiType.IMAGE_CHAT -> viewModel.imageResponse.observeAsState().value?.toList()
        ApiType.DOCUMENT_CHAT -> viewModel.documentResponse.observeAsState().value?.toList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if ((response != null) && response.isEmpty()) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.no_message_icon),
                contentDescription = "no message"
            )
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = "No messages yet. Start Chatting!",
                fontWeight = FontWeight.W500,
                fontSize = 15.sp
            )
        }
    }
    LazyColumn(
        reverseLayout = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(response!!.reversed()) { message ->
            MessageItem(text = message.text, mode = message.mode)
        }
    }
}