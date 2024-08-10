package teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.LanguageOptionModel
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.Message
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.MessageDao
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.Mode
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain.GeminiRepository
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain.GeminiRepositoryImpl
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain.GeminiService
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import javax.inject.Inject

@HiltViewModel
class GeminiAIViewModel @Inject constructor(
    private val dao: MessageDao
) : ViewModel() {
    private val _singleResponse = MutableLiveData(mutableStateListOf<Message>())
    val singleResponse: LiveData<SnapshotStateList<Message>> = _singleResponse

    private val _conversationList = MutableLiveData(mutableStateListOf<Message>())
    val conversationList: LiveData<SnapshotStateList<Message>> = _conversationList

    private val _imageResponse = MutableLiveData(mutableStateListOf<Message>())
    val imageResponse: LiveData<SnapshotStateList<Message>> = _imageResponse

    private val _documentResponse = MutableLiveData(mutableStateListOf<Message>())
    val documentResponse: LiveData<SnapshotStateList<Message>> = _documentResponse


    val languageOptionItems = listOf(
        LanguageOptionModel(1, "English"),
        LanguageOptionModel(2, "Swahili"),
        LanguageOptionModel(3, "French")
    )
    private val _selectedLanguageOption = MutableStateFlow("English")
    val selectedLanguageOption: StateFlow<String> get() = _selectedLanguageOption


    private var model: GenerativeModel? = null
    private var visionModel: GenerativeModel? = null
    private var documentModel: GenerativeModel? = null
    private var chat: Chat? = null

    init {
        dao.getAllMessage().observeForever { allMessages ->
            if (allMessages != null) {
                val snapshotStateList = convertToSnapshotStateList(allMessages)
                _conversationList.postValue(snapshotStateList)
            }
        }
    }

    fun updateSelectedLanguageOption(newValue: String){
        _selectedLanguageOption.value = newValue
    }

    fun makeSingleTurnQuery(context: Context, prompt: String) {
        _singleResponse.value?.clear()
        _singleResponse.value?.add(Message(text = prompt, mode = Mode.USER))
        _singleResponse.value?.add(
            Message(
                text = "Generating",
                mode = Mode.GEMINI,
                isGenerating = true
            )
        )
        if (model == null) {
            viewModelScope.launch {
                model = getModel(key = BuildConfig.GEMINI_API_KEY)
            }
        }
        makeGeneralQuery(
            apiType = ApiType.SINGLE_CHAT,
            result = _singleResponse,
            feed = prompt,
            supportingText = "in English",
            alternativeSupportingText = "in English"
        )
    }

    fun makeMultiTurnQuery(
        context: Context,
        prompt: String,
        supportingText: String? = null,
        alternativeSupportingText: String? = ": English ",
    ) {
        _conversationList.value?.add(Message(text = prompt, mode = Mode.USER))
        _conversationList.value?.add(
            Message(
                text = "generating...",
                mode = Mode.GEMINI,
                isGenerating = true
            )
        )

        //initiating Generative Model if not present
        if (model == null) {
            viewModelScope.launch {
                model = getModel(key = BuildConfig.GEMINI_API_KEY)
            }
        }
        //initiating Generative Chat if not present
        if (chat == null) {
            chat = getChat()
        }

        makeGeneralQuery(
            apiType = ApiType.MULTI_CHAT,
            result = _conversationList,
            feed = prompt,
            supportingText = ": give response in $supportingText Language",
            alternativeSupportingText = alternativeSupportingText
        )
    }

    fun makeImageQuery(
        context: Context,
        prompt: String,
        bitmaps: List<Bitmap>,
        supportingText: String? = null,
        alternativeSupportingText: String? = ": English ",
    ) {
        _imageResponse.value?.clear()
        _imageResponse.value?.add(Message(text = prompt, mode = Mode.USER))
        _imageResponse.value?.add(
            Message(
                text = "Generating...",
                mode = Mode.GEMINI,
                isGenerating = true
            )
        )
        if (visionModel == null) {
            viewModelScope.launch {
                visionModel = getModel(key = BuildConfig.GEMINI_API_KEY, vision = true)
            }
        }
        val inputContent = content {
//            bitmaps.take(4).forEach {
//                image(it)
//            }
            bitmaps.forEach {
                image(it)
            }
            text("$prompt: in $supportingText")
        }
        makeGeneralQuery(
            apiType = ApiType.IMAGE_CHAT,
            result = _imageResponse,
            feed = inputContent,
            supportingText = ": give response in $supportingText Language",
            alternativeSupportingText = alternativeSupportingText
        )
    }


    fun makeDocumentQuery(prompt: String) {
        println("DOCUMENT MODEL=========>")
        _documentResponse.value?.clear()
        _documentResponse.value?.add(Message(text = prompt, mode = Mode.USER))
        _documentResponse.value?.add(
            Message(
                text = "Generating.......",
                mode = Mode.GEMINI,
                isGenerating = true
            )
        )
        if (documentModel == null) {
            viewModelScope.launch {
                documentModel = getGeminiProModel(key = BuildConfig.GEMINI_API_KEY)
            }
        }

        val inputContent = content {
            text(prompt)
        }



        makeGeneralQuery(
            apiType = ApiType.DOCUMENT_CHAT,
            result = _documentResponse,
            feed = prompt,
            supportingText = "in English",
            alternativeSupportingText = "in English"
        )
    }



    fun clearContext() {
        _conversationList.value?.clear()
        chat = getChat()
        viewModelScope.launch {
            dao.deleteAllMessages()
        }
    }


    private fun makeGeneralQuery(
        apiType: ApiType,
        result: MutableLiveData<SnapshotStateList<Message>>,
        feed: Any,
        supportingText: Any?,
        alternativeSupportingText: String?
    ) {
        viewModelScope.launch {
            var output = ""
            try {
                val stream = when (apiType) {
                    ApiType.SINGLE_CHAT -> model?.generateContentStream(feed as String + supportingText as String)
                    ApiType.MULTI_CHAT -> chat?.sendMessageStream(feed as String + supportingText as String)
                    ApiType.IMAGE_CHAT -> visionModel?.generateContentStream(feed as Content)
                    ApiType.DOCUMENT_CHAT -> documentModel?.generateContentStream(feed as String)
                }

                stream?.collect { chunk ->
                    output += chunk.text.toString()
                    output.trimStart()
                    result.value?.set(
                        result.value!!.lastIndex,
                        Message(
                            text = output,
                            mode = Mode.GEMINI,
                            isGenerating = true
                        )
                    )
                }

                result.value?.set(
                    result.value!!.lastIndex,
                    Message(
                        text = output,
                        mode = Mode.GEMINI,
                        isGenerating = false
                    )
                )

                if (apiType == ApiType.MULTI_CHAT) {
                    viewModelScope.launch {
                        dao.upsertMessage(
                            Message(
                                text = feed as String,
                                mode = Mode.USER,
                                isGenerating = false,
                                obfuscatedText = supportingText.toString(),
                                alternateText = alternativeSupportingText ?: ""
                            )
                        )
                        dao.upsertMessage(
                            Message(
                                text = output,
                                mode = Mode.GEMINI,
                                isGenerating = false
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                result.value?.set(
                    result.value!!.lastIndex,
                    Message(
                        text = e.message.toString(),
                        mode = Mode.GEMINI,
                        isGenerating = false
                    )
                )
            }
        }
    }



    private fun getChat() = model?.startChat(generatePreviousChats())

    private fun getModel(key: String, vision: Boolean = false) =
        GenerativeModel(
            modelName = if (vision) "gemini-1.5-flash" else "gemini-pro",
            apiKey = key,
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
            )
        )

    private fun getGeminiProModel(key: String) =
        GenerativeModel(
            modelName = "gemini-1.5-pro-latest",
            apiKey = key,
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
            )
        )

    private fun generatePreviousChats(): List<Content> {
        val history = mutableListOf<Content>()
        for (message in conversationList.value.orEmpty()) {
            history.add(content(role = if (message.mode == Mode.USER) "user" else "model") {
                text(message.text)
            })
        }
        return history
    }

    private fun convertToSnapshotStateList(
        messages: List<Message>
    ): SnapshotStateList<Message> {
        return mutableStateListOf(*messages.toTypedArray())
    }



    private val geminiRepository: GeminiRepository = GeminiRepositoryImpl()

    private val geminiService = GeminiService()

    fun generateDocumentContent(
        message: String,
        images: List<ByteArray> = emptyList()
    ) {
        viewModelScope.launch {
//            val response = geminiRepository.generate(message, images)

            val response = geminiService.generateContentWithFiles(message, images)

        }
    }
}