package teka.android.organiks_platform_android.presentation.aiadvice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import teka.android.organiks_platform_android.data.remote.retrofit.models.OpenAiPromptModel
import teka.android.organiks_platform_android.data.room_remote_sync.models.OpenAiSearchResponse
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.SecondaryColor
import teka.android.organiks_platform_android.ui.widgets.SearchComposable

@SuppressLint("FlowOperatorInvokedInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AiAdviceScreen(){

    val aiAdviceViewModel: AiAdviceviewModel = hiltViewModel();
    val searchResultsState: State<OpenAiSearchResponse?> = aiAdviceViewModel.searchResults.observeAsState(null)

    // Access the searchResultsState.value in your Composable
    val searchResults = searchResultsState.value
    val error by aiAdviceViewModel.errorMessage.observeAsState("")
    var searchText by remember { mutableStateOf("") }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                aiAdviceViewModel.performSearch(OpenAiPromptModel(searchText))
            },
                backgroundColor = PrimaryColor
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Type your question ...",
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Advice Section",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h6
            )
//            SearchComposable { query ->
////                attendeeViewModel.onSearchQueryChanged(query)
//                aiAdviceViewModel.performSearch(OpenAiPromptModel(query))
//            }
            // TextField for entering the search query
            TextField(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                },
                label = { Text(text = "Search here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display search results or error message
            if (error.isNotBlank()) {
                Text(text = error, color = Color.Red)
            } else if (searchResults != null) {
                // Customize how you want to display the single result
                searchResults.result?.let { it1 -> Text(text = it1) }
                // Add other UI components as needed for the single result
            }

        }
    }

}



