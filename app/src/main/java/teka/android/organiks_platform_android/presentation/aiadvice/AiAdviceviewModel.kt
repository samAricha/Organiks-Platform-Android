package teka.android.organiks_platform_android.presentation.aiadvice

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.remote.retrofit.AiRetrofitProvider
import teka.android.organiks_platform_android.data.remote.retrofit.AiSearchService
import teka.android.organiks_platform_android.data.remote.retrofit.models.OpenAiPromptModel
import teka.android.organiks_platform_android.data.room_remote_sync.models.OpenAiSearchResponse
import javax.inject.Inject


@HiltViewModel
class AiAdviceviewModel @Inject constructor(
    private val application: Application
    ): ViewModel() {

//    // Flow for search query
//    private val searchQuery = MutableStateFlow("")
//
//
//    fun onSearchQueryChanged(query: String) {
//        searchQuery.value = query
//    }
//
//
//    private val _searchResultText = MutableStateFlow("")
//    val searchResultText: StateFlow<String> = _searchResultText.asStateFlow()
//
//    // Function to perform a search and update the search result text
//    fun search(query: String) {
//        // Here, you can implement your logic to fetch search results based on the query
//        // For this example, we'll just set some sample text as the search result
//        val searchResult = "Search results for: $query\n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
//                "Praesent eget semper mi."
//
//        // Update the search result text
//        _searchResultText.value = searchResult
//    }


    private val aiSearchService: AiSearchService = AiRetrofitProvider.creatOpenAiPromptService()
    // Define a LiveData to hold search results or error messages
    val searchResults: MutableLiveData<OpenAiSearchResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    // Define a function to make a network request and handle the response
    fun performSearch(openAiPromptModel: OpenAiPromptModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = aiSearchService.getOpenAiResponse(openAiPromptModel)
                searchResults.postValue(response)
            } catch (e: Exception) {
                errorMessage.postValue("Error: ${e.message}")
            }
        }
    }

}