package teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.BuildConfig
import teka.android.organiks_platform_android.data.remote.retrofit.models.EggCollectionResult
import teka.android.organiks_platform_android.data.remote.retrofit.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.retrofit.models.MilkCollectionResult
import teka.android.organiks_platform_android.domain.repository.RemoteEggRecordsRepository
import teka.android.organiks_platform_android.domain.repository.RemoteFruitRecordsRepository
import teka.android.organiks_platform_android.domain.repository.RemoteMilkRecordsRepository
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.AnalystMessage
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.Message
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.MessageDao
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.Mode
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain.GeminiRepository
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain.GeminiRepositoryImpl
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.domain.GeminiService
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GeminiAnalystViewModel @Inject constructor(
    private val dao: MessageDao,
    private val eggRecordsRepository: RemoteEggRecordsRepository,
    private val milkRecordsRepository: RemoteMilkRecordsRepository,
    private val fruitsRecordsRepository: RemoteFruitRecordsRepository,
) : ViewModel() {

    private val _conversationList = MutableLiveData(mutableStateListOf<AnalystMessage>())
    val conversationList: LiveData<SnapshotStateList<AnalystMessage>> = _conversationList

    private val _eggCollections = MutableStateFlow<List<EggCollectionResult>>(emptyList())
    val eggCollections: StateFlow<List<EggCollectionResult>> = _eggCollections.asStateFlow()

    private val _milkCollections = MutableStateFlow<List<MilkCollectionResult>>(emptyList())
    val milkCollections: StateFlow<List<MilkCollectionResult>> = _milkCollections.asStateFlow()

    private val _fruitCollections = MutableStateFlow<List<FruitCollectionDto>>(emptyList())
    val fruitCollections: StateFlow<List<FruitCollectionDto>> = _fruitCollections.asStateFlow()

    // State for loading indicator and error states
    private val _isFarmDataLoading = MutableStateFlow(false)
    val isFarmDataLoading: StateFlow<Boolean> get() = _isFarmDataLoading

    private val _farmDataErrorMessage = MutableStateFlow<String?>(null)
    val farmDataErrorMessage: StateFlow<String?> = _farmDataErrorMessage

    private val _farmDataSuccessMessage = MutableStateFlow<String?>(null)
    val farmDataSuccessMessage: StateFlow<String?> = _farmDataSuccessMessage

    private val _selectedLanguageOption = MutableStateFlow("")
    val selectedLanguageOption: StateFlow<String> get() = _selectedLanguageOption
    private val _selectedFarmerDataOption = MutableStateFlow("")
    val selectedFarmerDataOption: StateFlow<String> get() = _selectedFarmerDataOption

    private val _requestOptionData = MutableStateFlow<Any>(emptyList<Any>())
    val requestOptionData: StateFlow<Any> get() = _requestOptionData


    private var model: GenerativeModel? = null
    private var visionModel: GenerativeModel? = null
    private var documentModel: GenerativeModel? = null
    private var chat: Chat? = null

    init {
        dao.getAllAnalystMessage().observeForever { allMessages ->
            if (allMessages != null) {
                val snapshotStateList = convertAnalystMessageToSnapshotStateList(allMessages)
                _conversationList.postValue(snapshotStateList)
            }
        }
        viewModelScope.launch {
            remoteFarmDataInitialization()
        }
        viewModelScope.launch {
            _selectedFarmerDataOption.collect { selectedOption ->
                when (selectedOption) {
                    "Eggs Data" -> _requestOptionData.value = _eggCollections.value
                    "Milk Data" -> _requestOptionData.value = _milkCollections.value
                    "Fruit Data" -> _requestOptionData.value = _fruitCollections.value
                    else -> _requestOptionData.value = emptyList<Any>()
                }
            }
        }
    }

    fun onLanguageOptionChange(newValue: String){
        _selectedLanguageOption.value = newValue
    }

    fun onFarmerDataOptionChange(newValue: String){
        _selectedFarmerDataOption.value = newValue
    }


    fun makeMultiTurnAnalyticalQuery(
        context: Context,
        prompt: String,
        supportingText: String? = null,
        alternativeSupportingText: String? = ": FARMER DATA",
    ) {
        _conversationList.value?.add(AnalystMessage(text = prompt+alternativeSupportingText, mode = Mode.USER))
        _conversationList.value?.add(
            AnalystMessage(
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

        makeAnalyticalQuery(
            apiType = ApiType.MULTI_CHAT,
            result = _conversationList,
            feed = prompt,
            supportingText = supportingText,
            alternativeSupportingText = alternativeSupportingText
        )
    }



    fun clearContext() {
        _conversationList.value?.clear()
        chat = getChat()
        viewModelScope.launch {
            dao.deleteAllMessages()
        }
    }


    private fun makeAnalyticalQuery(
        apiType: ApiType,
        result: MutableLiveData<SnapshotStateList<AnalystMessage>>,
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
                        AnalystMessage(
                            text = output,
                            mode = Mode.GEMINI,
                            isGenerating = true
                        )
                    )
                }

                result.value?.set(
                    result.value!!.lastIndex,
                    AnalystMessage(text = output, mode = Mode.GEMINI, isGenerating = false)
                )

                if (apiType == ApiType.MULTI_CHAT) {
                    viewModelScope.launch {
                        dao.upsertAnalystMessage(
                            AnalystMessage(
                                text = feed as String + alternativeSupportingText as String,
                                mode = Mode.USER,
                                isGenerating = false,
                                obfuscatedText = supportingText.toString(),
                                alternateText = alternativeSupportingText ?: ""
                            )
                        )
                        dao.upsertAnalystMessage(
                            AnalystMessage(
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
                    AnalystMessage(
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

    private fun generatePreviousChats(): List<Content> {
        val history = mutableListOf<Content>()
        for (message in conversationList.value.orEmpty()) {
            history.add(content(role = if (message.mode == Mode.USER) "user" else "model") {
                text(message.text)
            })
        }
        return history
    }

    private fun convertAnalystMessageToSnapshotStateList(
        messages: List<AnalystMessage>
    ): SnapshotStateList<AnalystMessage> {
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



    private fun remoteFarmDataInitialization(){
        viewModelScope.launch {
            fetchAllEggRecords()
            fetchAllMilkCollections()
            fetchAllFruitCollections()
        }
    }




    private fun fetchAllEggRecords() {
        viewModelScope.launch {
            _isFarmDataLoading.value = true
            try {
                eggRecordsRepository.getAllEggCollections().collect { eggs ->
                    _eggCollections.value = eggs
                    Timber.tag(">>>EGGS LIST").d(eggs.toString())
                }
                _farmDataSuccessMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                _farmDataErrorMessage.value = e.message
            } finally {
                _isFarmDataLoading.value = false
            }
        }
    }


    private fun fetchAllFruitCollections() {
        viewModelScope.launch {
            _isFarmDataLoading.value = true
            try {
                fruitsRecordsRepository.getAllFruitCollections().collect { fruits ->
                    _fruitCollections.value = fruits
                    Timber.tag(">>>FRUIT LIST").d(fruits.toString())
                }
                _farmDataSuccessMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                _farmDataErrorMessage.value = e.message

            } finally {
                _isFarmDataLoading.value = false
            }
        }
    }

    private fun fetchAllMilkCollections() {
        viewModelScope.launch {
            _isFarmDataLoading.value = true
            try {
                milkRecordsRepository.getAllMilkCollections().collect { milk ->
                    _milkCollections.value = milk
                    Timber.tag(">>>MILK LIST").d(milk.toString())
                }
                _farmDataSuccessMessage.value = "Data Fetched successfully"

            } catch (e: Exception) {
                _farmDataErrorMessage.value = e.message

            } finally {
                _isFarmDataLoading.value = false
            }
        }
    }
}