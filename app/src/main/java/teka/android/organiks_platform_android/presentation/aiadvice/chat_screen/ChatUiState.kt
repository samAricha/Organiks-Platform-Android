package teka.android.organiks_platform_android.presentation.aiadvice.chat_screen


import teka.android.organiks_platform_android.domain.models.AiChatMessageModel
import teka.android.organiks_platform_android.domain.models.ChatMessageModel
import teka.android.organiks_platform_android.domain.models.ChatStatusModel

data class ChatUiState(
    val messages: List<ChatMessageModel> = emptyList(),
    val chatMessages: List<AiChatMessageModel> = emptyList(),
    val status: ChatStatusModel = ChatStatusModel.Idle,
    val apiKey: String = ""
)