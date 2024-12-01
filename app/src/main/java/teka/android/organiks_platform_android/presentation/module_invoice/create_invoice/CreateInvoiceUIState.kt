package teka.android.organiks_platform_android.presentation.module_invoice.create_invoice

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.util.TextFieldStateMngr
import teka.android.organiks_platform_android.util.today


@Stable
data class CreateInvoiceUiState(
    var unitPrice: TextFieldStateMngr = (TextFieldStateMngr(labelText = "unit price")),
    var totalAmount: TextFieldStateMngr = (TextFieldStateMngr(labelText = "total amount")),
    var issuerName: TextFieldStateMngr = (TextFieldStateMngr(labelText = "issucer Name")),
    var issuerPhone: TextFieldStateMngr = (TextFieldStateMngr(labelText = "issuer Phone")),
    var issuerEmail: TextFieldStateMngr = (TextFieldStateMngr(labelText = "issuer Email")),
    var currentFruitCollection: FruitCollectionEntity? = null,
    var selectedCustomer: CustomerEntity? = null,
    var isFetchingFruitCollection: Boolean = false,
    var isFormSubmissionSuccessful: Boolean = false,
    var errorMessage: String = "",
    var customerSearchQuery: String = "",
    var customerEntityList: List<CustomerEntity> = emptyList(),
    var showDatePickerDialog: Boolean = false,
    var showTimePickerDialog: Boolean = false,
    var showBottomSheet: Boolean = false,
    var date: LocalDateTime = today(),
    var time: LocalTime = today().time,
){
    companion object {
        val default = CreateInvoiceUiState()
    }
}