package teka.android.organiks_platform_android.presentation.module_fruits.list_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.domain.repository.DbRepository
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

const val FruitList_VM_TAG = "FruitList_VM_TAG"

@HiltViewModel
class FruitRecordsListViewModel @Inject constructor(
    private val appContext: Context,
    private val dbRepository: DbRepository,
) : ViewModel() {
    // UI state holder
    private val _customerListUIState = MutableStateFlow(FruitRecordsUIState())
    val customerListUIState: StateFlow<FruitRecordsUIState> = _customerListUIState


    init {
        Timber.tag(FruitList_VM_TAG).i("init function now")
        observeFruitList()
    }

    private fun observeFruitList() {
        viewModelScope.launch{
            launch{
                dbRepository
                    .getFruitCollections
                    .collectLatest { vehicleList ->
                        updateModelField(FruitRecordsUIState::fruitRecordsList, vehicleList)
                    }
            }
        }
    }


    fun onDateRangeSelected(selectedRange: Pair<Long?, Long?>) {
        Timber.tag(FruitList_VM_TAG).i("selectedDateRange:: $selectedRange")
        updateModelField(FruitRecordsUIState::selectedDateRange, selectedRange)
    }

    fun toggleDatePickerDialog(show: Boolean) {
        updateModelField(FruitRecordsUIState::showDatePickerDialog, show)
    }


    fun updateGatelogSearchQuery(query: String) {
        updateModelField(FruitRecordsUIState::fruitSearchQuery, query)
        filterGateLogs(query)
    }

    private fun filterGateLogs(query: String) {
        viewModelScope.launch {
            dbRepository.getFruitCollections.collect { vehicleList ->
                val filteredList = if (query.isBlank()) {
                    vehicleList
                } else {
                    vehicleList.filter { vehicle ->
                        vehicle.searchableString.contains(query, ignoreCase = true)
                    }
                }
                updateModelField(FruitRecordsUIState::fruitRecordsList, filteredList)
            }
        }
    }


    /// UI STATE UPDATES
    fun <T> updateModelField(property: KMutableProperty1<FruitRecordsUIState, T>, value: T) {
        _customerListUIState.update { currentState ->
            currentState.copy().also { newState ->
                property.set(newState, value)
            }
        }
    }

}
