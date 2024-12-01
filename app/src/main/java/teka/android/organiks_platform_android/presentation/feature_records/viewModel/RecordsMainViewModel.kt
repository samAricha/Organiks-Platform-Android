package teka.android.organiks_platform_android.presentation.feature_records.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.entities.EggCollection
import teka.android.organiks_platform_android.data.room.entities.MilkCollection

class RecordsMainViewModel:ViewModel() {

    var eggCollectionsResponse: List<EggCollection> by mutableStateOf(listOf())
    var milkCollectionsResponse: List<MilkCollection> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun  getEggCollectionList(){
        viewModelScope.launch {

            try {
//                val eggCollectionList = RetrofitProvider.createEggCollectionService().getAllEggCollections()
//                eggCollectionsResponse = eggCollectionList.results.map { it.toEggCollection() }

            }catch (e:Exception){
                errorMessage = e.message.toString();
            }
        }
    }

    fun  getMilkCollectionList(){
        viewModelScope.launch {

            try {
//                val milkCollectionList = RetrofitProvider.createMilkCollectionService().getAllMilkCollection()
//                milkCollectionsResponse = milkCollectionList.results.map { it.toMilkCollection() }

            }catch (e:Exception){
                errorMessage = e.message.toString();
            }
        }
    }


}