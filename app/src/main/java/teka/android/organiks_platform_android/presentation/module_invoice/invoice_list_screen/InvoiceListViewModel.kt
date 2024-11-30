package teka.android.organiks_platform_android.presentation.module_invoice.invoice_list_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InvoiceListViewModel @Inject constructor(
    private val appContext: Context
) : ViewModel() {

    fun getInvoiceFiles(): List<File> {
        val directory = appContext.getExternalFilesDir(null)
        return directory?.listFiles { _, name -> name.endsWith(".pdf") }?.toList() ?: emptyList()
    }
}
