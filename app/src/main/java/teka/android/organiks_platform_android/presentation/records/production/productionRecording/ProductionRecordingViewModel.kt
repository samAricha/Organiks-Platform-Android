package teka.android.organiks_platform_android.presentation.records.production.productionRecording

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.di.OrganiksDI
import teka.android.organiks_platform_android.repository.Repository
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import java.util.*

class ProductionRecordingViewModel
    constructor(
        private val eggCollectionId: Int,
        private val repository: Repository = OrganiksDI.repository
    ): ViewModel(){

    var state by mutableStateOf(ProductionRecordingState())
        private set

    init {


        if(eggCollectionId != -1){
            viewModelScope.launch {
                repository.getEggCollectionById(eggCollectionId)
                    .collectLatest {
//                        val eggType: Flow<EggType> = repository.getEggTypeById(it.eggTypeId);
//
//                        var eggTypeName = ""
//                        eggType.collect { eggType ->
//                            // Access the properties of the EggType object here
//                            eggTypeName = eggType.name
//                        }
                        state = state.copy(
                            eggTypeId = it.eggTypeId,
                            date = it.date,
                            eggCollectionQty = it.qty,
                            eggsCracked = it.cracked
                        ) }
            }
        }

    }

    init {
        state = if (eggCollectionId != -1){
            state.copy(isUpdatingItem = true)
        }else{
            state.copy(isUpdatingItem = false)
        }
    }


    //methods for modifying state

    fun onEggTypeId(newValue: Int){
        state = state.copy(eggTypeId = newValue)
    }

    fun onQtyChange(newValue: String){
        state = state.copy(eggCollectionQty = newValue)
    }

    fun onDateChange(newValue: Date){
        state = state.copy(date = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean){
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    fun addEggCollection(){
        viewModelScope.launch {
            repository.insertEggCollection(
                EggCollection(
                    date = state.date,
                    qty = state.eggCollectionQty,
                    cracked = state.eggsCracked,
                    eggTypeId = state.eggTypeId

                )
            )
        }
    }

    fun updateEggCollection(id: Int){
        viewModelScope.launch {
            repository.insertEggCollection(
                EggCollection(
                    date = state.date,
                    qty = state.eggCollectionQty,
                    cracked = state.eggsCracked,
                    eggTypeId = state.eggTypeId,
                    id = id
                )
            )
        }
    }

    fun addEggType(){
        viewModelScope.launch {
            repository.insertEggType(
                EggType(
                    id = state.eggTypeId,
                    name = state.eggTypeName
                )
            )
        }
    }

    fun getEggTypes(){
        viewModelScope.launch {
            repository.eggTypes.collectLatest {
                state = state.copy(eggTypes = it)
            }
        }
    }



}

class ProductionRecordingViewModelFactory(private val id: Int):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductionRecordingViewModel(eggCollectionId = id) as T
    }
}

data class ProductionRecordingState(
    val eggTypes: List<EggType> = emptyList(),
    val eggTypeId: Int = 0,
    val eggCollectionQty: String = "",
    val eggTypeName : String = "",
    val eggsCracked: Int = 0,
    val date: Date = Date(),
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
)