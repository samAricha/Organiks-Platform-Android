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
import teka.android.organiks_platform_android.data.room.models.ProductionCategory
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
        addProductionCategories()
        getEggTypes()

        if(eggCollectionId != -1){
            viewModelScope.launch {
                repository.getEggCollectionById(eggCollectionId)
                    .collectLatest {
                        state = state.copy(
                            date = Date(it.date),
                            eggCollectionQty = it.qty,
                            eggsCracked = it.cracked,
                            productionCategory = Utils.productionCategory.find { c ->
                                c.id == 0
                            } ?: Category(),
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

    val isFieldNotEmpty: Boolean
        get() = state.eggTypes.isNotEmpty() &&
                state.eggCollectionQty.isNotEmpty()


    //methods for modifying state
    fun onCategoryChange(newValue: Category){
        state = state.copy(productionCategory = newValue)
    }
    fun onEggTypeChange(newValue: String){
        state = state.copy(eggTypeName = newValue)
    }


    fun onQtyChange(newValue: String){
        state = state.copy(eggCollectionQty = newValue)
    }
    fun onCrackedQtyChange(newValue: String){
        state = state.copy(eggsCracked = newValue)
    }

    fun onDateChange(newValue: Date){
        state = state.copy(date = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean){
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    fun onSaveEggCollection(){
        viewModelScope.launch {
            repository.insertEggCollection(
                EggCollection(
                    date = state.date.time,
                    qty = state.eggCollectionQty,
                    cracked = state.eggsCracked,
                    eggTypeId = state.eggTypes.find {
                        it.name == state.eggTypeName
                    }?.id ?: 0,
                    isBackedUp = false
                )
            )
        }
    }


    //THIS IS CURRENTLY EGG COLLECTION BUT SHOULD BE
    //ADD CATEGORY ITEM
    private fun addProductionCategories(){
        viewModelScope.launch {
            Utils.productionCategory.forEach{
                repository.insertProductionCategory(
                    ProductionCategory(
                        id = it.id,
                        name = it.title
                    )
                )

            }
        }
    }




    fun addEggCollection(){
        viewModelScope.launch {
            repository.insertEggCollection(
                EggCollection(
                    date = state.date.time,
                    qty = state.eggCollectionQty,
                    cracked = state.eggsCracked,
                    eggTypeId = state.eggTypes.find {
                        it.name == state.eggTypeName
                    }?.id ?: 0

                )
            )
        }
    }

    fun updateEggCollection(id: Int){
        viewModelScope.launch {
            repository.insertEggCollection(
                EggCollection(
                    date = state.date.time,
                    qty = state.eggCollectionQty,
                    cracked = state.eggsCracked,
                    eggTypeId = state.eggTypes.find {
                        it.name == state.eggTypeName
                    }?.id ?: 0,
                    id = id
                )
            )
        }
    }

    fun addEggType(){
        viewModelScope.launch {
            repository.insertEggType(
                EggType(
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
    val eggCollectionQty: String = "",
    var eggTypeName : String = "",
    val eggsCracked: String = "",
    val date: Date = Date(),
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
    val productionCategory: Category = Category()
)