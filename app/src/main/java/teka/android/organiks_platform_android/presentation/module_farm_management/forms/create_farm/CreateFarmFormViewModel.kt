package teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_farm


import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.entities.FarmEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.formattedTimeBasedOnTimeFormat
import teka.android.organiks_platform_android.util.toEpochMillis
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

const val CreateFarmForm_VM_TAG = "CreateFarmForm_VM_TAG"

@HiltViewModel
class CreateFarmFormViewModel @Inject constructor(
    private val appContext: Context,
    private val repository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // UI state holder
    private val _createFarmFormUiState = MutableStateFlow(CreateFarmFormUIState())
    val createFarmFormUiState: StateFlow<CreateFarmFormUIState> = _createFarmFormUiState

    fun saveFarmEntity(){
        viewModelScope.launch {
            repository.insertFarmEntity(
                FarmEntity(
                    name = createFarmFormUiState.value.farmName.text,
                    categoryId = createFarmFormUiState.value.selectedCategory?.id.toString(),
                    categoryName = createFarmFormUiState.value.selectedCategory?.name.toString(),
                    subCategoryId = createFarmFormUiState.value.selectedSubcategory?.id.toString(),
                    subCategoryName = createFarmFormUiState.value.selectedSubcategory?.name.toString(),
                    date = createFarmFormUiState.value.date.toEpochMillis(),
                    time = createFarmFormUiState.value.time.formattedTimeBasedOnTimeFormat(24),
                    isBackedUp = false
                )
            )
        }
    }


    //////////////////// STATE UPDATES ////////////////////////

    fun updateStringField(property: KMutableProperty1<CreateFarmFormUIState, TextFieldStateMngr>, value: String) {
        _createFarmFormUiState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
    fun <T> updateModelField(property: KMutableProperty1<CreateFarmFormUIState, T>, value: T) {
        _createFarmFormUiState.update { currentState ->
            currentState.copy().apply {
                property.set(this, value)
            }
        }
    }

}
