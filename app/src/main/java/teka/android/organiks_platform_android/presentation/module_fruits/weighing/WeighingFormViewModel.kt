package teka.android.organiks_platform_android.presentation.module_fruits.weighing


import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.entities.WeighingEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.formattedTimeBasedOnTimeFormat
import teka.android.organiks_platform_android.util.toEpochMillis
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

private const val WeighingForm_VM_TAG = "WeighingForm_VM_TAG"

@HiltViewModel
class WeighingFormViewModel @Inject constructor(
    private val appContext: Context,
    private val repository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val fruitCollectionUUId: String? = savedStateHandle["fruitCollectionId"]


    // UI state holder
    private val _weighingFormUIState = MutableStateFlow(WeighingFormUIState())
    val weighingFormUIState: StateFlow<WeighingFormUIState> = _weighingFormUIState

    init {
        observeWeightList()
    }


    private fun observeWeightList() {
        viewModelScope.launch{
            launch{
                repository
                    .getWeights
                    .collectLatest { weightList ->
                        Timber.tag("WFVM").i("weightList:: $weightList")
                        updateModelField(WeighingFormUIState::weightList, weightList)
                    }
            }
        }
    }

    fun addWeight(){
        updateModelField(WeighingFormUIState::isSavingFormData, true)
        viewModelScope.launch {
            repository.insertWeight(
                WeighingEntity(
                    weight = weighingFormUIState.value.fruitWeight.text,
                    binNumber = "1",
                    fruitCollectionId = fruitCollectionUUId!!,
                    createdAt = System.currentTimeMillis(),
                )
            )
            updateModelField(WeighingFormUIState::isSavingFormData, false)
            clearInputFields()
        }

    }

    fun clearInputFields(){
        updateStringField(WeighingFormUIState::fruitWeight, "")
    }



    //////////////////// STATE UPDATES ////////////////////////

    fun updateStringField(property: KMutableProperty1<WeighingFormUIState, TextFieldStateMngr>, value: String) {
        _weighingFormUIState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
    fun <T> updateModelField(property: KMutableProperty1<WeighingFormUIState, T>, value: T) {
        _weighingFormUIState.update { currentState ->
            currentState.copy().apply {
                property.set(this, value)
            }
        }
    }

}
