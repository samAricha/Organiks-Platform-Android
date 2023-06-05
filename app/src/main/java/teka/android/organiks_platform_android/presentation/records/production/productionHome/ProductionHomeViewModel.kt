package teka.android.organiks_platform_android.presentation.records.production.productionHome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.di.OrganiksDI
import teka.android.organiks_platform_android.repository.Repository
import teka.android.organiks_platform_android.ui.Category


class HomeViewModel(
    private val repository: Repository = OrganiksDI.repository
): ViewModel() {

    var state by mutableStateOf(ProductionHomeState())
        private set

    init {
        getEggCollections()
    }



    private fun getEggCollections(){
        viewModelScope.launch {
            repository.getEggCollections.collectLatest {
                //state = state.copy(items = it)
            }
        }
    }

    fun deleteEggCollections(eggCollection: EggCollection){
        viewModelScope.launch {
            repository.deleteCollection(eggCollection)
        }

    }


    fun onEggTypeChange(category: Category){
        state = state.copy(category = category)
        filterBy(category.id)
    }

    fun onEggCollectionCheckedChange(eggCollection: EggCollection, isChecked: Boolean){
        viewModelScope.launch {
            repository.updateEggCollection(
                eggCollection = eggCollection.copy(isChecked = isChecked)
            )
        }
    }



    private fun filterBy(collectionId:Int){
        if(collectionId != 10001){
            viewModelScope.launch {
                repository.getEggCollectionById(
                    collectionId
                ).collectLatest {
                    //state = state.copy(eggCollections = it)
                }
            }
        }else{
            getEggCollections()
        }


    }


}

data class ProductionHomeState(
    val eggCollections: List<EggCollection> = emptyList(),
    val category: Category = Category(),
    val itemChecked: Boolean = false

)