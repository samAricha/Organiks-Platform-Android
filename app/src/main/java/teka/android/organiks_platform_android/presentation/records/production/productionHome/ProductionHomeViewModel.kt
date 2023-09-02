package teka.android.organiks_platform_android.presentation.records.production.productionHome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.EggTypeEggCollectionItem
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room_remote_sync.RemoteDataUpdater
import teka.android.organiks_platform_android.repository.DbRepository
import teka.android.organiks_platform_android.ui.Category
import javax.inject.Inject

@HiltViewModel
class ProductionHomeViewModel @Inject constructor(
    private val repository: DbRepository,
    private val remoteDataUpdater: RemoteDataUpdater
): ViewModel() {

    var state by mutableStateOf(ProductionHomeState())
        private set

    init {
        getEggCollectionsWithEggTypes()
    }


    private fun getEggCollectionsWithEggTypes(){
        viewModelScope.launch {
            repository.getEggCollections.collectLatest {
//                Log.d(TAG, "INSIDE GET EGG COLLECTION$it")
                state = state.copy(eggCollections = it)
            }
        }
    }

    private fun getEggCollections(){
        viewModelScope.launch {
            repository.getEggCollections.collectLatest {
                state = state.copy(eggCollections = it)
            }
        }
    }

    private fun getEggTypeById(id: Int){

        viewModelScope.launch {
            repository.getEggTypeById(id)
        }

    }

    fun deleteEggCollections(eggCollection: EggCollection){
        viewModelScope.launch {
            repository.deleteCollection(eggCollection)
        }

    }


//    fun onProductionCategoryChange(category: Category){
//        state = state.copy(category = category)
//        filterBy(category.id)
//    }

    fun onEggCollectionCheckedChange(eggCollection: EggCollection, isChecked: Boolean){
        viewModelScope.launch {
            repository.updateEggCollection(
                eggCollection = eggCollection.copy(isBackedUp = isChecked)
            )
        }
    }



    private fun filterBy(categoryId:Int){
        if(categoryId != 10001){
            viewModelScope.launch {
                repository.getEggCollectionById(
                    categoryId
                ).collectLatest {
                    //state = state.copy(eggCollections = it)
                }
            }
        }else{
            getEggCollections()
        }
    }


    //Synchronizing local Room to Remote db
    fun syncRoomDbToRemote() {

        viewModelScope.launch {
            //filter and get eggCollections with status backedUp == false
            val notBackedUpEggCollections = state.eggCollections.filter { eggCollection ->
                !eggCollection.isBackedUp
            }
            remoteDataUpdater.updateRemoteEggCollectionData(notBackedUpEggCollections, repository)
        }
    }


}

data class ProductionHomeState(
    val eggCollectionsWithTypesList: List<EggTypeEggCollectionItem> = emptyList(),
    val eggCollections: List<EggCollection> = emptyList(),
    val category: Category = Category(),
    val itemChecked: Boolean = false

)