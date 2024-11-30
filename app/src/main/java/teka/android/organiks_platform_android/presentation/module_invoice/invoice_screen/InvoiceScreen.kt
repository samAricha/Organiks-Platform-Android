package teka.android.organiks_platform_android.presentation.module_invoice.invoice_screen

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import timber.log.Timber

@Composable
fun InvoiceScreen(
    navController: NavController,
    viewModel: InvoiceViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Button(onClick = {
        val filePath = viewModel.generateSampleInvoice()
        if (filePath != null) {
            Timber.tag("Invoice Screen::filePath").i(filePath)
            Toast.makeText(context, "Invoice saved at: $filePath", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Failed to generate invoice.", Toast.LENGTH_LONG).show()
        }
    }) {
        Text("Generate Invoice")
    }
}
