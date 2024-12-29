package teka.android.organiks_platform_android.presentation.module_invoice.invoice_list_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.presentation.module_fruits.list_screen.FruitList_VM_TAG
import teka.android.organiks_platform_android.presentation.module_fruits.list_screen.FruitRecordsUIState
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InvoiceListViewModel @Inject constructor(
    private val appContext: Context,
    private val dbRepository: DbRepository
) : ViewModel() {

    private val _invoiceList = MutableStateFlow<List<InvoiceEntity>>(emptyList<InvoiceEntity>())
    val invoiceList: StateFlow<List<InvoiceEntity>> = _invoiceList

    fun getInvoiceFiles(): List<File> {
        val directory = appContext.getExternalFilesDir(null)
        return directory?.listFiles { _, name -> name.endsWith(".pdf") }?.toList() ?: emptyList()
    }

    init {
        observeInvoiceList()
    }

    private fun observeInvoiceList() {
        viewModelScope.launch{
            launch{
                dbRepository
                    .getAllInvoices
                    .collectLatest { vehicleList ->
                        _invoiceList.value = vehicleList
                    }
            }
        }
    }


}
