package teka.android.organiks_platform_android.util.helpers;

import me.kariot.invoicegenerator.data.ModelInvoiceFooter
import me.kariot.invoicegenerator.data.ModelInvoiceHeader
import me.kariot.invoicegenerator.data.ModelInvoiceInfo
import me.kariot.invoicegenerator.data.ModelInvoiceItem
import me.kariot.invoicegenerator.data.ModelInvoicePriceInfo
import me.kariot.invoicegenerator.data.ModelTableHeader
import teka.android.organiks_platform_android.domain.services.InvoiceGeneratorHelper
import teka.android.organiks_platform_android.presentation.module_invoice.create_invoice.CreateInvoiceUiState

class InvoiceGenerator {

    fun generateInvoice(createInvoiceUiState: CreateInvoiceUiState, invoiceHelper: InvoiceGeneratorHelper): String? {
        // Header data
        val headerData = ModelInvoiceHeader(
            phoneNumber = createInvoiceUiState.issuerPhone.text,
            emailAddress = createInvoiceUiState.issuerEmail.text,
            websiteURL = "www.organiks.com",
            address = ModelInvoiceHeader.ModelAddress("", "", "")
        )

        // Customer info
        val customerInfo = createInvoiceUiState.selectedCustomer?.let {
            ModelInvoiceInfo.ModelCustomerInfo(
                name = it.name,
                addressLine1 = it.phone,
                addressLine2 = "",
                addressLine3 = ""
            )
        }

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
                createInvoiceUiState.currentFruitCollection?.fruitTypeId ?: "",
                "Fruit",
                "Description 1",
                createInvoiceUiState.unitPrice.text,
                createInvoiceUiState.currentFruitCollection!!.qty ,
                formatCash(createInvoiceUiState.totalAmount.text)
            ),
            ModelInvoiceItem(
                "Expenditure",
                "Fruit",
                "Description 1",
                createInvoiceUiState.totalExpenses.text,
                "1",
                formatCash(createInvoiceUiState.totalExpenses.text)
            )
        )

        // Price info
        val priceInfo = ModelInvoicePriceInfo(
            subTotal = createInvoiceUiState.totalAmount.text,
            taxTotal = "0",
            invoiceTotal = formatCash((createInvoiceUiState.totalAmount.text.toDouble() + createInvoiceUiState.totalExpenses.text.toDouble()).toString())
        )

        // Footer data
        val footerData = ModelInvoiceFooter("Thank you for your business!")

        // Call helper to generate the invoice
        return invoiceHelper.generateInvoice(
            fileName = "Invoice_0002",
            headerData = headerData,
            customerInfo = customerInfo ?: throw IllegalArgumentException("Customer info is required"),
            invoiceNumber = "INV-0002",
            invoiceDate = createInvoiceUiState.date.date.toString(),
            invoiceAmount = formatCash((createInvoiceUiState.totalAmount.text.toDouble() + createInvoiceUiState.totalExpenses.text.toDouble()).toString()),
            tableHeader = tableHeader,
            tableData = tableData,
            priceInfo = priceInfo,
            footerData = footerData
        )
    }
}
