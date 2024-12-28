package teka.android.organiks_platform_android.presentation.syncing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.domain.repository.def.CustomerRepository
import teka.android.organiks_platform_android.domain.repository.def.FruitCollectionRepository
import teka.android.organiks_platform_android.domain.repository.def.InvoiceRepository
import teka.android.organiks_platform_android.util.Resource
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val invoiceRepository: InvoiceRepository,
    private val fruitCollectionRepository: FruitCollectionRepository
) : ViewModel() {

    private val _syncState = MutableStateFlow<Resource<Unit>>(Resource.Idle())
    val syncState: StateFlow<Resource<Unit>> = _syncState

    fun syncAllDataSequentially() {
        viewModelScope.launch {
            _syncState.value = Resource.Loading()

            try {
                // Sequential sync
                customerRepository.syncCustomers().collect { resource ->
                    handleSyncResource(resource, "Customer sync failed")
                }

                invoiceRepository.syncInvoices().collect { resource ->
                    handleSyncResource(resource, "Invoice sync failed")
                }

                fruitCollectionRepository.syncFruitCollections().collect { resource ->
                    handleSyncResource(resource, "Fruit collection sync failed")
                }

                _syncState.value = Resource.Success(Unit) // Success state
            } catch (e: Exception) {
                _syncState.value = Resource.Error(e.localizedMessage ?: "Error syncing data")
            }
        }
    }

    fun syncAllDataAsync() {
        viewModelScope.launch {
            _syncState.value = Resource.Loading()

            try {
                coroutineScope {
                    val customerSync = async { customerRepository.syncCustomers().collect { resource ->
                        handleSyncResource(resource, "Customer sync failed")
                    } }
                    val invoiceSync = async { invoiceRepository.syncInvoices().collect{ resource ->
                        handleSyncResource(resource, "Invoice sync failed")
                    } }
                    val fruitSync = async { fruitCollectionRepository.syncFruitCollections().collect{ resource ->
                        handleSyncResource(resource, "Fruit collection sync failed")
                    } }

                    // Await all syncs
                    customerSync.await()
                    invoiceSync.await()
                    fruitSync.await()
                }

                _syncState.value = Resource.Success(Unit) // Success state
            } catch (e: Exception) {
                _syncState.value = Resource.Error(e.localizedMessage ?: "Error syncing data")
            }
        }
    }


    private fun <T> handleSyncResource(resource: Resource<T>, errorMessage: String) {
        if (resource is Resource.Error) {
            throw Exception(resource.message ?: errorMessage)
        }
    }
}
