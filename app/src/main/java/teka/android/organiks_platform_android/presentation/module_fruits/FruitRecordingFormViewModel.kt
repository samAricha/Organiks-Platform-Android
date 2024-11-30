package teka.android.organiks_platform_android.presentation.module_fruits


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
import retrofit2.Response
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

const val FruitForm_VM_TAG = "FruitForm_VM_TAG"

@HiltViewModel
class AvoRejectsFormViewModel @Inject constructor(
    private val appContext: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // UI state holder
    private val _avoRejectsFormUIState = MutableStateFlow(FruitRecordingFormUIState())
    val avoRejectsFormUIState: StateFlow<FruitRecordingFormUIState> = _avoRejectsFormUIState



    //////////////////// STATE UPDATES ////////////////////////

    fun updateStringField(property: KMutableProperty1<FruitRecordingFormUIState, TextFieldStateMngr>, value: String) {
        _avoRejectsFormUIState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
    fun <T> updateModelField(property: KMutableProperty1<FruitRecordingFormUIState, T>, value: T) {
        _avoRejectsFormUIState.update { currentState ->
            currentState.copy().apply {
                property.set(this, value)
            }
        }
    }

}
