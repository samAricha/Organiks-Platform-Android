package teka.android.organiks_platform_android.presentation.module_farm_management.screens.my_farm.tabs.my_expenditure

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

private const val MyExpenditure_VM_TAG = "MyExpenditure_VM_TAG"


@HiltViewModel
class MyExpenditureViewModel @Inject constructor(
    private val appContext: Context,
    private val dbRepository: DbRepository,
) : ViewModel() {
    // UI state holder
    private val _expenditureListUIState = MutableStateFlow(ExpenditureListUIState())
    val expenditureListUIState: StateFlow<ExpenditureListUIState> = _expenditureListUIState


    init {
        Timber.Forest.tag(MyExpenditure_VM_TAG).i("init function now")
        observeCustomerList()
    }

    private fun observeCustomerList() {
        viewModelScope.launch {
            launch{
                dbRepository
                    .getMyFarmsList
                    .collectLatest { customerList ->
                        updateModelField(ExpenditureListUIState::farmList, customerList)
                    }
            }
        }
    }


    fun onDateRangeSelected(selectedRange: Pair<Long?, Long?>) {
        Timber.Forest.tag(MyExpenditure_VM_TAG).i("selectedDateRange:: $selectedRange")
        updateModelField(ExpenditureListUIState::selectedDateRange, selectedRange)
    }

    fun toggleDatePickerDialog(show: Boolean) {
        updateModelField(ExpenditureListUIState::showDatePickerDialog, show)
    }


    fun updateFarmSearchQuery(query: String) {
        updateModelField(ExpenditureListUIState::farmSearchQuery, query)
        filterGateLogs(query)
    }

    private fun filterGateLogs(query: String) {
        viewModelScope.launch {
            dbRepository.getMyFarmsList.collect { farmList ->
                val filteredList = if (query.isBlank()) {
                    farmList
                } else {
                    farmList.filter { vehicle ->
                        vehicle.searchableString.contains(query, ignoreCase = true)
                    }
                }
                updateModelField(ExpenditureListUIState::farmList, filteredList)
            }
        }
    }


    /// UI STATE UPDATES
    fun <T> updateModelField(property: KMutableProperty1<ExpenditureListUIState, T>, value: T) {
        _expenditureListUIState.update { currentState ->
            currentState.copy().also { newState ->
                property.set(newState, value)
            }
        }
    }

}