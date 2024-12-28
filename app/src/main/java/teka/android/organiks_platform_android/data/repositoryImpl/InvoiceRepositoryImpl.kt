package teka.android.organiks_platform_android.data.repositoryImpl

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.models.toInvoiceEntity
import teka.android.organiks_platform_android.data.remote.models.toInvoiceEntityList
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.domain.repository.InvoiceRepository
import teka.android.organiks_platform_android.util.Resource


class InvoiceRepositoryImpl @Inject constructor(
    private val firebaseRemoteDatabase: FirebaseRemoteDatabase,
) : InvoiceRepository {

    override fun getInvoices(): Flow<Resource<List<InvoiceEntity>>> =  flow{
        emit(Resource.Loading())
        try {
            val invoiceList = firebaseRemoteDatabase.fetchInvoiceList()
            emit(Resource.Success(invoiceList.toInvoiceEntityList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching invoices"))
        }
    }

    override fun getInvoiceById(id: String): Flow<Resource<InvoiceEntity>> = flow{
        emit(Resource.Loading())
        try {
            val invoice = firebaseRemoteDatabase.fetchInvoiceById(id)
            emit(Resource.Success(invoice.toInvoiceEntity()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching invoice with ID: $id"))
        }    }

    override fun createInvoice(invoice: InvoiceEntity): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            firebaseRemoteDatabase.addInvoice(invoice)
            emit(Resource.Success(Unit)) // Indicate success with no specific data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error creating invoice"))
        }
    }

}