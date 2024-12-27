package teka.android.organiks_platform_android.presentation.module_farm_management.screens.my_farm.tabs.my_inventory

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
import teka.android.organiks_platform_android.presentation.module_farm_management.tabs.my_farms_list.MyFarmsListUIState
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
    private val _myFarmListUIState = MutableStateFlow(MyFarmsListUIState())
    val myFarmListUIState: StateFlow<MyFarmsListUIState> = _myFarmListUIState


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
                        updateModelField(MyFarmsListUIState::farmList, customerList)
                    }
            }
        }
    }


    fun onDateRangeSelected(selectedRange: Pair<Long?, Long?>) {
        Timber.Forest.tag(MyExpenditure_VM_TAG).i("selectedDateRange:: $selectedRange")
        updateModelField(MyFarmsListUIState::selectedDateRange, selectedRange)
    }

    fun toggleDatePickerDialog(show: Boolean) {
        updateModelField(MyFarmsListUIState::showDatePickerDialog, show)
    }


    fun updateFarmSearchQuery(query: String) {
        updateModelField(MyFarmsListUIState::farmSearchQuery, query)
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
                updateModelField(MyFarmsListUIState::farmList, filteredList)
            }
        }
    }


    /// UI STATE UPDATES
    fun <T> updateModelField(property: KMutableProperty1<MyFarmsListUIState, T>, value: T) {
        _myFarmListUIState.update { currentState ->
            currentState.copy().also { newState ->
                property.set(newState, value)
            }
        }
    }

}