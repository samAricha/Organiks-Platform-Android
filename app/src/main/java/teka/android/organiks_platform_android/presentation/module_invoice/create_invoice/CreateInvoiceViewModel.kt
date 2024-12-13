package teka.android.organiks_platform_android.presentation.module_invoice.create_invoice

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.kariot.invoicegenerator.data.ModelInvoiceFooter
import me.kariot.invoicegenerator.data.ModelInvoiceHeader
import me.kariot.invoicegenerator.data.ModelInvoiceInfo
import me.kariot.invoicegenerator.data.ModelInvoiceItem
import me.kariot.invoicegenerator.data.ModelInvoicePriceInfo
import me.kariot.invoicegenerator.data.ModelTableHeader
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.domain.services.InvoiceGeneratorHelper
import teka.android.organiks_platform_android.presentation.module_customers.form.AddCustomerFormUIState
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1
import kotlinx.coroutines.flow.onEach
import java.util.Locale


private const val CI_VM_TAG = "CI_VM_TAG"


@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(
    private val appContext: Context,
    savedStateHandle: SavedStateHandle,
    private val dbRepository: DbRepository
) : ViewModel() {
    val fruitCollectionUUId: String? = savedStateHandle["fruitCollectionId"]

    // UI state holder
    private val _createInvoiceUiState = MutableStateFlow(CreateInvoiceUiState())
    val createInvoiceUiState: StateFlow<CreateInvoiceUiState> = _createInvoiceUiState

    init {
        observeFruitCollection()
        observeCustomerList()
        observeTotalAmount()
    }

    private fun observeCustomerList() {
        viewModelScope.launch {
            dbRepository.getCustomers.collectLatest { customerEntityList ->
                updateModelField(CreateInvoiceUiState::customerEntityList, customerEntityList)
            }
        }
    }

    private fun observeFruitCollection() {
        viewModelScope.launch {
            if (fruitCollectionUUId == null) {
                Timber.tag(CI_VM_TAG).w("GateLog ID is null, skipping fetch")
                updateModelField(CreateInvoiceUiState::isFetchingFruitCollection, false)
                return@launch
            }

            updateModelField(CreateInvoiceUiState::isFetchingFruitCollection, true)

            runCatching {
                dbRepository.getFruitCollectionByUUId(fruitCollectionUUId).collectLatest { gateLogIntakeList ->
                    updateModelField(CreateInvoiceUiState::currentFruitCollection, gateLogIntakeList)
                }
            }.onFailure { e ->
                Timber.tag(CI_VM_TAG).e("Error fetching room data: ${e.localizedMessage}")
            }.also {
                updateModelField(CreateInvoiceUiState::isFetchingFruitCollection, false)
            }
        }
    }


    private fun observeTotalAmount() {
        combine(
            _createInvoiceUiState.map { it.unitPrice.text },
            _createInvoiceUiState.map { it.currentFruitCollection?.qty ?: "0" }
        ) { unitPrice, quantity ->
            val price = unitPrice.toDoubleOrNull() ?: 0.0
            val qty = quantity.toDoubleOrNull() ?: 0.0
            String.format(Locale.US, "%.2f", price * qty)
        }.onEach { total ->
            _createInvoiceUiState.update {
                it.copy(totalAmount = it.totalAmount.copy(text = total))
            }
        }.launchIn(viewModelScope)
    }




    private val invoiceHelper = InvoiceGeneratorHelper(appContext)

    fun generateInvoice(): String? {
        // Header data
        val headerData = ModelInvoiceHeader(
            phoneNumber = createInvoiceUiState.value.issuerPhone.text,
            emailAddress = createInvoiceUiState.value.issuerEmail.text,
            websiteURL = "www.organiks.com",
            address = ModelInvoiceHeader.ModelAddress(
                "",
                "",
                ""
            )
        )

        // Customer info
        val customerInfo = createInvoiceUiState.value.selectedCustomer?.let {
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
                createInvoiceUiState.value.currentFruitCollection!!.fruitTypeId,
                "item desc",
                "Description 1",
                createInvoiceUiState.value.unitPrice.text,
                createInvoiceUiState.value.currentFruitCollection!!.qty,
                createInvoiceUiState.value.totalAmount.text
            )
        )



        // Price info
        val priceInfo =   ModelInvoicePriceInfo(
            subTotal = createInvoiceUiState.value.unitPrice.text,
            taxTotal = "0",
            invoiceTotal = createInvoiceUiState.value.totalAmount.text
        )

        // Footer data
        val footerData = ModelInvoiceFooter("Thank you for your business!")

        // Call helper to generate the invoice
        return invoiceHelper.generateInvoice(
            fileName = "Invoice_0002",
            headerData = headerData,
            customerInfo = customerInfo!!,
            invoiceNumber = "INV-0002",
            invoiceDate = createInvoiceUiState.value.date.date.toString(),
            invoiceAmount = createInvoiceUiState.value.totalAmount.text,
            tableHeader = tableHeader,
            tableData = tableData,
            priceInfo = priceInfo,
            footerData = footerData
        )
    }


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


    //////////////////// STATE UPDATES ///////////////////////
    fun <T> updateModelField(property: KMutableProperty1<CreateInvoiceUiState, T>, value: T) {
        _createInvoiceUiState.update { currentState ->
            currentState.copy().also { newState ->
                property.set(newState, value)
            }
        }
    }

    fun updateStringField(property: KMutableProperty1<CreateInvoiceUiState, TextFieldStateMngr>, value: String) {
        _createInvoiceUiState.update { currentState ->
            val updatedState = property.get(currentState).copy(text = value)
            currentState.copy().also {
                property.set(it, updatedState)
            }
        }
    }
}
