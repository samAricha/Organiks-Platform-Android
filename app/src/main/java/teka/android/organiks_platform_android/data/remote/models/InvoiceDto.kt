package teka.android.organiks_platform_android.data.remote.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity

@Serializable
data class InvoiceDto(
    val uuid: String = "",
    val customerId: String = "",
    val toName: String = "",
    val toPhone: String = "",
    val fromName: String = "",
    val fromPhone: String = "",
    val totalAmount: String = "",
    val totalExpenses: String = "",
    val fruitCollection: String = "",
    val fromEmail: String = "",
    val toEmail: String = "",
    val unitPrice: String = "",
    val quantity: String = "",
    val date: String = "",
    val time: String = "",
    val isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

fun InvoiceDto.toInvoiceEntity(): InvoiceEntity {
    return InvoiceEntity(
        uuid = this.uuid.ifEmpty { InvoiceEntity.generateUniqueId() },
        customerId = this.customerId,
        toName = this.toName,
        toPhone = this.toPhone,
        fromName = this.fromName,
        fromPhone = this.fromPhone,
        totalAmount = this.totalAmount,
        totalExpenses = this.totalExpenses,
        fruitCollection = this.fruitCollection,
        date = this.date,
        time = this.time,
        isBackedUp = this.isBackedUp,
        createdAt = this.createdAt,
        toEmail = this.toEmail,
        fromEmail = this.fromEmail,
        unitPrice = this.unitPrice,
        quantity = this.quantity
    )
}

fun InvoiceEntity.toInvoiceDto(): InvoiceDto {
    return InvoiceDto(
        uuid = this.uuid,
        customerId = this.customerId,
        toName = this.toName,
        toPhone = this.toPhone,
        fromName = this.fromName,
        fromPhone = this.fromPhone,
        totalAmount = this.totalAmount,
        totalExpenses = this.totalExpenses,
        fruitCollection = this.fruitCollection,
        date = this.date,
        time = this.time,
        isBackedUp = this.isBackedUp,
        createdAt = this.createdAt
    )
}

fun List<InvoiceDto>.toInvoiceEntityList(): List<InvoiceEntity> {
    return this.map { it.toInvoiceEntity() }
}

fun List<InvoiceEntity>.toInvoiceDtoList(): List<InvoiceDto> {
    return this.map { it.toInvoiceDto() }
}
