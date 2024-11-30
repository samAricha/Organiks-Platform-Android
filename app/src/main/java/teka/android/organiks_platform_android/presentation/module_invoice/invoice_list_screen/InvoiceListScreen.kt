package teka.android.organiks_platform_android.presentation.module_invoice.invoice_list_screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.io.File

@Composable
fun InvoiceListScreen(
    navController: NavController,
    viewModel: InvoiceListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val invoiceFiles = remember { viewModel.getInvoiceFiles() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(invoiceFiles) { file ->
            InvoiceItem(file, onOpenInvoice = { openPdfFile(context, file) })
        }
    }
}

@Composable
fun InvoiceItem(file: File, onOpenInvoice: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onOpenInvoice() }
    ) {
        Text(
            text = file.name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun openPdfFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "teka.android.organiks.provider",
        file
    )
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent)
}
