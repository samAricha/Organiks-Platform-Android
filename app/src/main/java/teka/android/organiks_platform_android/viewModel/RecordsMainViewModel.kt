package teka.android.organiks_platform_android.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.room.models.EggCollection

class RecordsMainViewModel:ViewModel() {

    var eggCollectionsResponse: List<EggCollection> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun  getEggCollectionList(){
        viewModelScope.launch {

            try {
                val eggCollectionList = RetrofitProvider.createVisitorListService().getEggCollections()
                eggCollectionsResponse = eggCollectionList

            }catch (e:Exception){
                errorMessage = e.message.toString();
            }
        }
    }


}