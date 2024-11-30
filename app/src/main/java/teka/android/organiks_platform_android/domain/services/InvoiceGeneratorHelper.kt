package teka.android.organiks_platform_android.domain.services

import android.content.Context
import me.kariot.invoicegenerator.data.ModelInvoiceFooter
import me.kariot.invoicegenerator.data.ModelInvoiceHeader
import me.kariot.invoicegenerator.data.ModelInvoiceInfo
import me.kariot.invoicegenerator.data.ModelInvoiceItem
import me.kariot.invoicegenerator.data.ModelInvoicePriceInfo
import me.kariot.invoicegenerator.data.ModelTableHeader
import me.kariot.invoicegenerator.utils.InvoiceGenerator
import teka.android.organiks_platform_android.R
import timber.log.Timber

class InvoiceGeneratorHelper(private val context: Context) {

    // Generate invoice PDF
    fun generateInvoice(
        fileName: String,
        headerData: ModelInvoiceHeader,
        customerInfo: ModelInvoiceInfo.ModelCustomerInfo,
        invoiceNumber: String,
        invoiceDate: String,
        invoiceAmount: String,
        tableHeader: ModelTableHeader,
        tableData: List<ModelInvoiceItem>,
        priceInfo: ModelInvoicePriceInfo,
        footerData: ModelInvoiceFooter,
        currency: String = "$",
        invoiceColor: String = "#FF5722" // Default invoice color
    ): String? {
        return try {
            val invoiceInfo = ModelInvoiceInfo(
                customerDetails = customerInfo,
                invoiceNumber = invoiceNumber,
                invoiceDate = invoiceDate,
                invoiceTotal = invoiceAmount
            )
            Timber.tag("Invoice Helper::info").i(invoiceInfo.toString())


            val pdfGenerator: InvoiceGenerator = InvoiceGenerator(context).apply {
                setInvoiceLogo(R.drawable.egg100)
                setCurrency(currency)
                setInvoiceColor(invoiceColor)
                setInvoiceHeaderData(headerData)
                setInvoiceInfo(invoiceInfo)
                setInvoiceTableHeaderDataSource(tableHeader)
                setInvoiceTableData(tableData)
                setPriceInfoData(priceInfo)
                setInvoiceFooterData(footerData)
            }
            Timber.tag("Invoice Helper::pdfGen").i(pdfGenerator.toString())


            // Generate the PDF and return the file URI
            val fileUri = pdfGenerator.generatePDF("$fileName.pdf")
            Timber.tag("Invoice Helper::fileUri").i(fileUri.toString())
            Timber.tag("Invoice Helper::filePath").i(fileUri.path)
            fileUri.path // Return the file path
        } catch (e: Exception) {
            Timber.tag("Invoice Helper::gen").i(e.localizedMessage)
            e.printStackTrace()
            null // Return null if an error occurs
        }
    }
}
