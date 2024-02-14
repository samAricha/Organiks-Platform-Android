package teka.android.organiks_platform_android.presentation.aiadvice.chat_screen

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.data.gemini.repository.GeminiRepositoryImpl
import teka.android.organiks_platform_android.domain.models.AiChatMessageModel
import teka.android.organiks_platform_android.domain.models.ChatMessageModel
import teka.android.organiks_platform_android.domain.models.ChatStatusModel
import teka.android.organiks_platform_android.domain.models.Sender
import teka.android.organiks_platform_android.domain.repository.GeminiRepository
import javax.inject.Inject

@HiltViewModel
class ChatViewModel  @Inject constructor(
) : ViewModel() {

    private val geminiRepository: GeminiRepository = GeminiRepositoryImpl()

    private val _uiState = mutableStateOf(ChatUiState())
    val uiState: State<ChatUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(apiKey = geminiRepository.getApiKey())
        }
    }

    fun setApiKey(key: String) {
        geminiRepository.setApiKey(key)
        _uiState.value = _uiState.value.copy(apiKey = key, status = ChatStatusModel.Success("API key updated successfully."))
    }

    fun generateContent(message: String, images: List<ByteArray> = emptyList()) {
        viewModelScope.launch {
            addToMessages(message, images, Sender.User)
            addToMessages("", emptyList(), Sender.Bot, true)

            when (val response = geminiRepository.generate(message, images)) {
                is ChatStatusModel.Success -> updateLastBotMessage(response.data, response)
                is ChatStatusModel.Error -> updateLastBotMessage(response.message, response)
                else -> {}
            }
        }
    }

    fun generateGeminiResponse(message: String, images: List<ByteArray> = emptyList()) {
        viewModelScope.launch {
            addToMessages(message, images, Sender.User)
            addToMessages("", emptyList(), Sender.Bot, true)

            when (val response = geminiRepository.generate(message)) {
                is ChatStatusModel.Success -> updateLastBotMessage(response.data, response)
                is ChatStatusModel.Error -> updateLastBotMessage(response.message, response)
                else -> {}
            }
        }
    }

    private fun updateLastBotMessage(text: String, status: ChatStatusModel) {
        val messages = _uiState.value.messages.toMutableList()
        if (messages.isNotEmpty() && messages.last().sender == Sender.Bot) {
            val last = messages.last()
            val updatedMessage = last.copy(text = text, isLoading = status == ChatStatusModel.Loading)
            messages[messages.lastIndex] = updatedMessage
            _uiState.value = _uiState.value.copy(
                messages = messages,
                status = status
            )
        }
    }

    private fun addToMessages(
        text: String,
        images: List<ByteArray>,
        sender: Sender,
        isLoading: Boolean = false
    ) {
        val message = ChatMessageModel(sender, text, images, isLoading)
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + message,
            status = if (isLoading) ChatStatusModel.Loading else ChatStatusModel.Idle
        )
    }

//    private fun addToChatMessages2(
//        text: String,
//        images: List<Bitmap>,
//        sender: Sender,
//        isLoading: Boolean = false
//    ) {
//        val message = AiChatMessageModel(sender, text, images, isLoading)
//        _uiState.value = _uiState.value.copy(
//            chatMessages = _uiState.value.chatMessages + message,
//            status = if (isLoading) ChatStatusModel.Loading else ChatStatusModel.Idle
//        )
//    }

//    private fun addToChatMessages(
//        text: String,
//        images: List<ByteArray>,
//        sender: Sender,
//        isLoading: Boolean = false
//    ) {
//        val message = AiChatMessageModel(sender, text, images, isLoading)
//        _uiState.value = _uiState.value.copy(
//            chatMessages = _uiState.value.chatMessages + message,
//            status = if (isLoading) ChatStatusModel.Loading else ChatStatusModel.Idle
//        )
//    }








    val generativeTextModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    val generativeImageModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    fun geminiText(){
        viewModelScope.launch {
            val prompt = "Write a story about a magic backpack."
            val response = generativeTextModel.generateContent(prompt)
        }
    }
    fun geminiImage(images:List<Bitmap>, prompt:String){
        viewModelScope.launch {
            val inputContent = content {
                for (inputImage in images) {
                    image(inputImage)
                }
                text("What's different between these pictures?")
            }
            val response = generativeImageModel.generateContent(inputContent)

        }
    }
}