package teka.android.organiks_platform_android.presentation.module_customers.form


import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.models.CustomerEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.formattedTimeBasedOnTimeFormat
import teka.android.organiks_platform_android.util.toEpochMillis
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

const val AddCustomerForm_VM_TAG = "AddCustomerForm_VM_TAG"

@HiltViewModel
class AddCustomerFormViewModel @Inject constructor(
    private val appContext: Context,
    private val repository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // UI state holder
    private val _addCustomerFormUiState = MutableStateFlow(AddCustomerFormUIState())
    val addCustomerFormUiState: StateFlow<AddCustomerFormUIState> = _addCustomerFormUiState

    fun saveCustomerEntity(){
        viewModelScope.launch {
            repository.insertCustomerEntity(
                CustomerEntity(
                    name = addCustomerFormUiState.value.customerName.text,
                    phone = addCustomerFormUiState.value.customerPhone.text,
                    email = addCustomerFormUiState.value.customerEmail.text,
                    product = addCustomerFormUiState.value.fruitType.text,
                    date = addCustomerFormUiState.value.date.toEpochMillis(),
                    time = addCustomerFormUiState.value.time.formattedTimeBasedOnTimeFormat(24),
                    isBackedUp = false
                )
            )
        }
    }


    //////////////////// STATE UPDATES ////////////////////////

    fun updateStringField(property: KMutableProperty1<AddCustomerFormUIState, TextFieldStateMngr>, value: String) {
        _addCustomerFormUiState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
    fun <T> updateModelField(property: KMutableProperty1<AddCustomerFormUIState, T>, value: T) {
        _addCustomerFormUiState.update { currentState ->
            currentState.copy().apply {
                property.set(this, value)
            }
        }
    }

}
