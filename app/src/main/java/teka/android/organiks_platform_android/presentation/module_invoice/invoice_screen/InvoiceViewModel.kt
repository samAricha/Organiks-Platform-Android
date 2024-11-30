package teka.android.organiks_platform_android.presentation.module_invoice.invoice_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.kariot.invoicegenerator.data.ModelInvoiceFooter
import me.kariot.invoicegenerator.data.ModelInvoiceHeader
import me.kariot.invoicegenerator.data.ModelInvoiceInfo
import me.kariot.invoicegenerator.data.ModelInvoiceItem
import me.kariot.invoicegenerator.data.ModelInvoicePriceInfo
import me.kariot.invoicegenerator.data.ModelTableHeader
import teka.android.organiks_platform_android.domain.services.InvoiceGeneratorHelper
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val appContext: Context
) : ViewModel() {

    private val invoiceHelper = InvoiceGeneratorHelper(appContext)

    fun generateSampleInvoice(): String? {
        // Header data
        val headerData = ModelInvoiceHeader(
            phoneNumber = "(123) 456-7890",
            emailAddress = "example@mail.com",
            websiteURL = "www.example.com",
            address = ModelInvoiceHeader.ModelAddress(
                "123 Main Street",
                "Suite 456",
                "City, State, ZIP"
            )
        )

        // Customer info
        val customerInfo = ModelInvoiceInfo.ModelCustomerInfo(
            name = "John Doe",
            addressLine1 = "456 Elm Street",
            addressLine2 = "Apt 7B",
            addressLine3 = "City, State, ZIP"
        )

        // Table header
        val tableHeader = ModelTableHeader(
            firstColoumn = "Item",
            secondColoumn = "Description",
            thirdColoumn = "Unit Price",
            fourthColoumn = "Quantity",
            fifthColoumn = "Total"
        )

        // Table data
        val tableData = arrayListOf(
            ModelInvoiceItem(
                "Item 1",
                "item desc",
                "Description 1",
                "50",
                "2",
                "100"
            ),
            ModelInvoiceItem(
                "Item 2",
                "item desc",
                "Description 2",
                "100",
                "1",
                "100"
            )
        )



        // Price info
        val priceInfo =   ModelInvoicePriceInfo(
            subTotal = "200",
            taxTotal = "20",
            invoiceTotal = "220"
        )

        // Footer data
        val footerData = ModelInvoiceFooter("Thank you for your business!")

        // Call helper to generate the invoice
        return invoiceHelper.generateInvoice(
            fileName = "Invoice_0002",
            headerData = headerData,
            customerInfo = customerInfo,
            invoiceNumber = "INV-0001",
            invoiceDate = "2024-11-30",
            invoiceAmount = "220",
            tableHeader = tableHeader,
            tableData = tableData,
            priceInfo = priceInfo,
            footerData = footerData
        )
    }
}
