package teka.android.organiks_platform_android.util.helpers;

import me.kariot.invoicegenerator.data.ModelInvoiceFooter
import me.kariot.invoicegenerator.data.ModelInvoiceHeader
import me.kariot.invoicegenerator.data.ModelInvoiceInfo
import me.kariot.invoicegenerator.data.ModelInvoiceItem
import me.kariot.invoicegenerator.data.ModelInvoicePriceInfo
import me.kariot.invoicegenerator.data.ModelTableHeader
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.domain.services.InvoiceGeneratorHelper

class InvoiceGenerator {

    fun generateInvoice(
        invoiceEntity: InvoiceEntity,
        invoiceHelper: InvoiceGeneratorHelper
    ): String? {
        // Header data
        val headerData = ModelInvoiceHeader(
            phoneNumber = invoiceEntity.fromPhone,
            emailAddress = invoiceEntity.fromEmail,
            websiteURL = "www.organiks.com",
            address = ModelInvoiceHeader.ModelAddress("", "", "")
        )

        // Customer info
        val customerInfo = ModelInvoiceInfo.ModelCustomerInfo(
            name = invoiceEntity.toName,
            addressLine1 = invoiceEntity.toPhone,
            addressLine2 = "",
            addressLine3 = ""
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
                invoiceEntity.fruitCollection,
                "Fruit",
                "Description 1",
                invoiceEntity.unitPrice,
                invoiceEntity.quantity ,
                formatCash(invoiceEntity.totalAmount)
            ),
            ModelInvoiceItem(
                "Expenditure",
                "Fruit",
                "Description 1",
                invoiceEntity.totalExpenses,
                "1",
                formatCash(invoiceEntity.totalExpenses)
            )
        )

        // Price info
        val priceInfo = ModelInvoicePriceInfo(
            subTotal = invoiceEntity.totalAmount,
            taxTotal = "0",
            invoiceTotal = formatCash((invoiceEntity.totalAmount.toDouble() + invoiceEntity.totalExpenses.toDouble()).toString())
        )

        // Footer data
        val footerData = ModelInvoiceFooter("Thank you for your business!")

        // Call helper to generate the invoice
        return invoiceHelper.generateInvoice(
            fileName = "Invoice_0002",
            headerData = headerData,
            customerInfo = customerInfo,
            invoiceNumber = "INV-0002",
            invoiceDate = invoiceEntity.date,
            invoiceAmount = formatCash((invoiceEntity.totalAmount.toDouble() + invoiceEntity.totalExpenses.toDouble()).toString()),
            tableHeader = tableHeader,
            tableData = tableData,
            priceInfo = priceInfo,
            footerData = footerData
        )
    }
}
