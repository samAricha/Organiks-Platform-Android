package teka.android.organiks_platform_android.presentation.module_customers.list_screen

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

private const val Customers_VM_TAG = "Customers_VM_TAG"

@HiltViewModel
class CustomerListViewModel @Inject constructor(
    private val appContext: Context,
    private val dbRepository: DbRepository,
) : ViewModel() {
    // UI state holder
    private val _customerListUIState = MutableStateFlow(CustomerListUIState())
    val customerListUIState: StateFlow<CustomerListUIState> = _customerListUIState


    init {
        Timber.tag(Customers_VM_TAG).i("init function now")
        observeCustomerList()
    }

    private fun observeCustomerList() {
        viewModelScope.launch{
            launch{
                dbRepository
                    .getCustomers
                    .collectLatest { customerList ->
                        updateModelField(CustomerListUIState::customerList, customerList)
                    }
            }
        }
    }


    fun onDateRangeSelected(selectedRange: Pair<Long?, Long?>) {
        Timber.tag(Customers_VM_TAG).i("selectedDateRange:: $selectedRange")
        updateModelField(CustomerListUIState::selectedDateRange, selectedRange)
    }

    fun toggleDatePickerDialog(show: Boolean) {
        updateModelField(CustomerListUIState::showDatePickerDialog, show)
    }


    fun updateCustomerSearchQuery(query: String) {
        updateModelField(CustomerListUIState::customerSearchQuery, query)
        filterGateLogs(query)
    }

    private fun filterGateLogs(query: String) {
        viewModelScope.launch {
            dbRepository.getCustomers.collect { customerList ->
                val filteredList = if (query.isBlank()) {
                    customerList
                } else {
                    customerList.filter { vehicle ->
                        vehicle.searchableString.contains(query, ignoreCase = true)
                    }
                }
                updateModelField(CustomerListUIState::customerList, filteredList)
            }
        }
    }


    /// UI STATE UPDATES
    fun <T> updateModelField(property: KMutableProperty1<CustomerListUIState, T>, value: T) {
        _customerListUIState.update { currentState ->
            currentState.copy().also { newState ->
                property.set(newState, value)
            }
        }
    }

}
