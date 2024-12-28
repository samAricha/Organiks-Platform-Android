package teka.android.organiks_platform_android.domain.repository

import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.util.Resource

interface InvoiceRepository {
    fun getInvoices(): Flow<Resource<List<InvoiceEntity>>>
    fun getInvoiceById(invoiceId: String): Flow<Resource<InvoiceEntity>>
    fun createInvoice(invoice: InvoiceEntity): Flow<Resource<Unit>>
    fun syncInvoices(): Flow<Resource<Unit>>
}