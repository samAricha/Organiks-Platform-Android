package teka.android.organiks_platform_android.data.repositoryImpl

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.models.toInvoiceEntity
import teka.android.organiks_platform_android.data.remote.models.toInvoiceEntityList
import teka.android.organiks_platform_android.data.room.RoomRepository
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.domain.repository.def.InvoiceRepository
import teka.android.organiks_platform_android.util.Resource


class InvoiceRepositoryImpl @Inject constructor(
    private val roomRepository: RoomRepository,
    private val firebaseRemoteDatabase1: FirebaseRemoteDatabase,
) : InvoiceRepository {


    // Sync invoices from Room to Firebase
    override fun syncInvoices(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading()) // Emit loading state before syncing

        try {
            // Fetch all unsynced invoices from Room database
            val unsyncedInvoices = roomRepository.getUnsyncedInvoices()

            // Iterate over the unsynced invoices and sync them to Firebase
            for (invoice in unsyncedInvoices) {
                // Add invoice to Firebase
                firebaseRemoteDatabase1.addInvoice(invoice)
                // After successful sync, mark the invoice as synced in Room
                roomRepository.markInvoiceAsSynced(invoice.uuid)
            }

            emit(Resource.Success(Unit)) // Emit success after syncing all invoices
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error syncing invoices")) // Emit error if something goes wrong
        }
    }

    override fun getInvoices(): Flow<Resource<List<InvoiceEntity>>> =  flow{
        emit(Resource.Loading())
        try {
            val invoiceList = firebaseRemoteDatabase1.fetchInvoiceList()
            emit(Resource.Success(invoiceList.toInvoiceEntityList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching invoices"))
        }
    }

    override fun getInvoiceById(id: String): Flow<Resource<InvoiceEntity>> = flow{
        emit(Resource.Loading())
        try {
            val invoice = firebaseRemoteDatabase1.fetchInvoiceById(id)
            emit(Resource.Success(invoice.toInvoiceEntity()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching invoice with ID: $id"))
        }    }

    override fun createInvoice(invoice: InvoiceEntity): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            firebaseRemoteDatabase1.addInvoice(invoice)
            emit(Resource.Success(Unit)) // Indicate success with no specific data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error creating invoice"))
        }
    }

}