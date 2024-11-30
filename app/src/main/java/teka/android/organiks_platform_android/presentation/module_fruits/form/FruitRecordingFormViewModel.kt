package teka.android.organiks_platform_android.presentation.module_fruits.form


import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.toEpochMillis
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

const val FruitForm_VM_TAG = "FruitForm_VM_TAG"

@HiltViewModel
class FruitRecordingFormViewModel @Inject constructor(
    private val appContext: Context,
    private val repository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // UI state holder
    private val _fruitRecordingFormUiState = MutableStateFlow(FruitRecordingFormUIState())
    val fruitRecordingFormUiState: StateFlow<FruitRecordingFormUIState> = _fruitRecordingFormUiState

    fun saveFruitCollection(){
        viewModelScope.launch {
            repository.insertFruitCollection(
                FruitCollectionEntity(
                    date = fruitRecordingFormUiState.value.date.toEpochMillis(),
                    qty = fruitRecordingFormUiState.value.fruitWeight.text,
                    fruitTypeId = fruitRecordingFormUiState.value.fruitType.text,
                    isBackedUp = false
                )
            )
        }
    }


    //////////////////// STATE UPDATES ////////////////////////

    fun updateStringField(property: KMutableProperty1<FruitRecordingFormUIState, TextFieldStateMngr>, value: String) {
        _fruitRecordingFormUiState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
    fun <T> updateModelField(property: KMutableProperty1<FruitRecordingFormUIState, T>, value: T) {
        _fruitRecordingFormUiState.update { currentState ->
            currentState.copy().apply {
                property.set(this, value)
            }
        }
    }

}
