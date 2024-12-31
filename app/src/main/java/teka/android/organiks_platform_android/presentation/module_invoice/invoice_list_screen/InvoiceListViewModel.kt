package teka.android.organiks_platform_android.presentation.module_invoice.invoice_list_screen

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.domain.services.InvoiceGeneratorHelper
import teka.android.organiks_platform_android.presentation.module_fruits.list_screen.FruitList_VM_TAG
import teka.android.organiks_platform_android.presentation.module_fruits.list_screen.FruitRecordsUIState
import teka.android.organiks_platform_android.util.helpers.InvoiceGenerator
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


    private val invoiceGenerator = InvoiceGenerator()
    private val invoiceHelper = InvoiceGeneratorHelper(appContext)


    fun generateInvoice(invoiceEntity: InvoiceEntity): String? {
        val filePath =  invoiceGenerator.generateInvoice(invoiceEntity, invoiceHelper)
        Timber.tag("INVOICE FILE PATH").i(filePath)
        return filePath
    }

    fun getInvoiceFile(invoiceEntity: InvoiceEntity): File? {
        // Generate the file name using UUID
        val fileName = "${invoiceEntity.uuid}.pdf"
        val directory = appContext.getExternalFilesDir(null) // Get the external files directory

        // Check if the directory exists
        return directory?.let {
            val file = File(it, fileName)

            // If the file doesn't exist, generate the invoice
            if (!file.exists()) {
                // Generate the invoice here, without using the returned path, just based on UUID
                generateInvoice(invoiceEntity)
            }

            // Return the file if it exists now
            if (file.exists()) {
                file
            } else {
                null // Return null if the file still doesn't exist after generation
            }
        }
    }

}
