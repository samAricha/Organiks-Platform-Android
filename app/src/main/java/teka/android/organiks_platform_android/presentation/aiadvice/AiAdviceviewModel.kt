package teka.android.organiks_platform_android.presentation.aiadvice

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class AiAdviceviewModel @Inject constructor(
    private val application: Application
    ): ViewModel() {

    // Flow for search query
    private val searchQuery = MutableStateFlow("")


    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }


    private val _searchResultText = MutableStateFlow("")
    val searchResultText: StateFlow<String> = _searchResultText.asStateFlow()

    // Function to perform a search and update the search result text
    fun search(query: String) {
        // Here, you can implement your logic to fetch search results based on the query
        // For this example, we'll just set some sample text as the search result
        val searchResult = "Search results for: $query\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "Praesent eget semper mi."

        // Update the search result text
        _searchResultText.value = searchResult
    }

}