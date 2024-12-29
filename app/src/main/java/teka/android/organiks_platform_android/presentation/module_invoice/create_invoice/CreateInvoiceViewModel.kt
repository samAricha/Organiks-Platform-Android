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
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.util.helpers.InvoiceGenerator
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

    private val invoiceGenerator = InvoiceGenerator()
    private val invoiceHelper = InvoiceGeneratorHelper(appContext)


    fun generateInvoice(invoiceEntity: InvoiceEntity): String? {
        return invoiceGenerator.generateInvoice(invoiceEntity, invoiceHelper)
    }
    
    fun createInvoice(){
        viewModelScope.launch {
            val invoice = createInvoiceUiState.value.selectedCustomer?.let {
                createInvoiceUiState.value.currentFruitCollection?.let { it1 ->
                    InvoiceEntity(
                        toName = it.name,
                        toPhone = it.phone,
                        fromName = createInvoiceUiState.value.issuerName.text,
                        fromPhone = createInvoiceUiState.value.issuerPhone.text,
                        totalAmount = createInvoiceUiState.value.totalAmount.text,
                        totalExpenses = createInvoiceUiState.value.totalExpenses.text,
                        fruitCollection = it1.qty,
                        date = createInvoiceUiState.value.date.date.toString(),
                        time = createInvoiceUiState.value.time.toString(),
                        customerId = it.uuid,
                        toEmail = createInvoiceUiState.value.selectedCustomer!!.email,
                        fromEmail = createInvoiceUiState.value.issuerEmail.text,
                        unitPrice = createInvoiceUiState.value.unitPrice.text,
                        quantity = createInvoiceUiState.value.currentFruitCollection!!.qty,
                    )
                }
            }
            var filePath:String? = null
            if (invoice != null) {
                dbRepository.insertInvoiceEntity(invoice)
                filePath = generateInvoice(invoice)
                if (filePath != null){
                    updateModelField(CreateInvoiceUiState::isFormSubmissionSuccessful, true)
                }
            }
        }
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
