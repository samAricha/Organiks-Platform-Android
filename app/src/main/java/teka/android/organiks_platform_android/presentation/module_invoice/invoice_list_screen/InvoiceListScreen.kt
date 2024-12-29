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
import teka.android.organiks_platform_android.navigation.AppScreens
import timber.log.Timber
import java.io.File

@Composable
fun InvoiceListScreen(
    navController: NavController,
    viewModel: InvoiceListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val invoiceFiles: List<File> = remember { viewModel.getInvoiceFiles() }
    val invoiceList = viewModel.invoiceList.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        items(invoiceFiles) { file ->
            InvoiceItem(file, onOpenInvoice = {
//                openPdfFile(context, file)
                navController.navigate(AppScreens.PDFViewerScreen.createRoute(file.absolutePath))
            })
        }
        items(invoiceList.value){ invoice ->
            InvoiceItemCard(invoice) {
                val file = viewModel.getInvoiceFile(invoice)
                if (file != null && file.exists()) {
                    // Use the file as needed
                    Timber.tag("INVOICE FILE").i("File path: ${file.absolutePath}")
                    navController.navigate(AppScreens.PDFViewerScreen.createRoute(file.absolutePath))
                } else {
                    Timber.tag("INVOICE FILE").e("Invoice file not found")
                }
            }
        }
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
