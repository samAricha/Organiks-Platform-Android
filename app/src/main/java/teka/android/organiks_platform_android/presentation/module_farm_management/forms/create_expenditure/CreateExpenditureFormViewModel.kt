package teka.android.organiks_platform_android.presentation.module_farm_management.forms.create_expenditure


import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.entities.ExpenditureEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.formattedTimeBasedOnTimeFormat
import teka.android.organiks_platform_android.util.toEpochMillis
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1



const val ExpenditureForm_VM_TAG = "ExpenditureForm_VM_TAG"

@HiltViewModel
class CreateExpenditureFormViewModel @Inject constructor(
    private val appContext: Context,
    private val repository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // UI state holder
    private val _expenditureFormUiState = MutableStateFlow(CreateExpenditureFormUIState())
    val expenditureFormUiState: StateFlow<CreateExpenditureFormUIState> = _expenditureFormUiState

    init {
        loadCurrentFarm()
    }

    fun saveExpenseEntity(){
        viewModelScope.launch {
            repository.insertExpenditureEntity(
                ExpenditureEntity(
                    expenditureTypeId = expenditureFormUiState.value.selectedCategory?.id.toString(),
                    expenditureTypeName = expenditureFormUiState.value.selectedCategory?.name.toString(),
                    expenditureSubTypeId = expenditureFormUiState.value.selectedSubcategory?.id.toString(),
                    expenditureSubTypeName = expenditureFormUiState.value.selectedSubcategory?.name.toString(),
                    date = expenditureFormUiState.value.date.toEpochMillis(),
                    time = expenditureFormUiState.value.time.formattedTimeBasedOnTimeFormat(24),
                    isBackedUp = false,
                    farmId = expenditureFormUiState.value.currentFarm?.farmId ?: "",
                    farmName = expenditureFormUiState.value.currentFarm?.name ?: "",
                    farmCategoryId = expenditureFormUiState.value.currentFarm?.categoryId ?: "",
                    farmCategoryName = expenditureFormUiState.value.currentFarm?.categoryName ?: "",
                    farmSubCategoryId = expenditureFormUiState.value.currentFarm?.subCategoryId
                        ?: "",
                    farmSubCategoryName = expenditureFormUiState.value.currentFarm?.subCategoryName
                        ?: "",
                    amount = expenditureFormUiState.value.amount.text,
                )
            )
        }
    }


    private fun loadCurrentFarm() {
        viewModelScope.launch {
            updateModelField(CreateExpenditureFormUIState::isFetchingData, true)

            runCatching {
                repository.getMyFarmsList.collect { farmsList ->
                    val firstFarm = farmsList.firstOrNull()
                    // Ensure `firstFarm` is not null before proceeding
                    firstFarm?.let {
                        updateModelField(CreateExpenditureFormUIState::currentFarm, it)
                    } ?: run {
                        // Handle the case where there is no farm
                        // For example, show an error message or perform some fallback logic
                    }
                }
            }.onFailure { e ->
                Timber.tag(ExpenditureForm_VM_TAG).e("Error fetching room data: ${e.localizedMessage}")
            }.also {
                updateModelField(CreateExpenditureFormUIState::isFetchingData, false)
            }
        }
    }


    //////////////////// STATE UPDATES ////////////////////////

    fun updateStringField(property: KMutableProperty1<CreateExpenditureFormUIState, TextFieldStateMngr>, value: String) {
        _expenditureFormUiState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
    fun <T> updateModelField(property: KMutableProperty1<CreateExpenditureFormUIState, T>, value: T) {
        _expenditureFormUiState.update { currentState ->
            currentState.copy().apply {
                property.set(this, value)
            }
        }
    }

}
