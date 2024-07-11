package teka.android.organiks_platform_android.presentation.feature_records.production.productionRecording

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.data.room.models.ProductionCategory
import teka.android.organiks_platform_android.repository.DbRepository
import teka.android.organiks_platform_android.ui.Category
import teka.android.organiks_platform_android.ui.Utils
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductionRecordingViewModel @Inject constructor(
        private val repository: DbRepository
    ): ViewModel(){

    // Mutable state for eggCollectionId
    private val _eggCollectionId = mutableStateOf(-1)
    val eggCollectionId: State<Int> get() = _eggCollectionId

    private val _milkCollectionQtyEntered = mutableStateOf("")
    val milkCollectionQtyEntered: State<String> get() = _milkCollectionQtyEntered

    var state by mutableStateOf(ProductionRecordingState())
        private set

    init {
        addProductionCategories()
        getEggTypes()

        if(eggCollectionId.value != -1){
            viewModelScope.launch {
                repository.getEggCollectionById(eggCollectionId.value)
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
        state = if (eggCollectionId.value != -1){
            state.copy(isUpdatingItem = true)
        }else{
            state.copy(isUpdatingItem = false)
        }
    }

    val isFieldNotEmpty: Boolean
        get() = state.eggTypes.isNotEmpty() &&
                state.eggCollectionQty.isNotEmpty()


    fun onMilkCollectionQtyChange(newValue: String) {
        _milkCollectionQtyEntered.value = newValue
    }
    fun onMilkCollectionQuantityChange(newValue: String) {
        _milkCollectionQtyEntered.value = newValue
    }

    //methods for modifying state
    fun onCategoryChange(newValue: Category){
        state = state.copy(productionCategory = newValue)
        state = state.copy(selectedProductionCategory = newValue)
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

    fun onFruitQtyChange(newValue: String){
        state = state.copy(fruitCollectionQty = newValue)
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

    fun onSaveFruitCollection(){
        viewModelScope.launch {
            repository.insertFruitCollection(
                FruitCollectionEntity(
                    date = state.date.time,
                    qty = state.fruitCollectionQty,
                    fruitTypeId = state.eggTypes.find {
                        it.name == state.eggTypeName
                    }?.id ?: 0,
                    isBackedUp = false
                )
            )
        }
    }

    fun saveMilkCollection() {
        viewModelScope.launch {
            // Get the milk collection quantity from the state
            val milkCollectionQty = milkCollectionQtyEntered.value

            // Check if the quantity is not empty
            if (milkCollectionQty.isNotEmpty()) {
                // Create a new MilkCollection object and save it
                repository.insertMilkCollection(
                    MilkCollection(
                        qty = milkCollectionQty,
                    )
                )
            }
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

    fun updateFruitCollection(id: Int){
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
data class ProductionRecordingState(
    val eggTypes: List<EggType> = emptyList(),
    val eggCollectionQty: String = "",
    var eggTypeName : String = "",
    val eggsCracked: String = "",
    val fruitCollectionQty: String = "",
    var fruitTypeName : String = "",
    val date: Date = Date(),
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
    val productionCategory: Category = Category(),
    val selectedProductionCategory: Category = Utils.productionCategory[0] // Set the default to "Eggs"
)