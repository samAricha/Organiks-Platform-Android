package teka.android.organiks_platform_android.data.remote.firebase

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import teka.android.organiks_platform_android.data.remote.models.CustomerDto
import teka.android.organiks_platform_android.data.remote.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.remote.models.InvoiceDto
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRemoteDatabase @Inject constructor(
    private val tenantContext: TenantContext
) {
    // ------------------ Fruit Collection Operations ------------------

    suspend fun fetchFruitCollectionList(): List<FruitCollectionDto> {
        val snapshot = tenantContext.getFruitCollection().get().await()
        return snapshot.toObjects(FruitCollectionDto::class.java)
    }

    suspend fun fetchFruitCollectionById(fruitCollectionId: String): FruitCollectionDto {
        val documentSnapshot = tenantContext.getFruitCollection().document(fruitCollectionId).get().await()
        return documentSnapshot.toObject(FruitCollectionDto::class.java)
            ?: throw Exception("Fruit Collection with ID $fruitCollectionId not found")
    }

    suspend fun addFruitCollection(fruitCollection: FruitCollectionEntity) {
        tenantContext.getFruitCollection().document(fruitCollection.uuid).set(fruitCollection).await()
    }

    suspend fun updateFruitCollection(updatedFruitCollection: FruitCollectionDto) {
        tenantContext.getFruitCollection().document(updatedFruitCollection.uuid).set(updatedFruitCollection).await()
    }

    // ------------------ Customer Collection Operations ------------------

    suspend fun fetchCustomerList(): List<CustomerDto> {
        val snapshot = tenantContext.getCustomerCollection().get().await()
        return snapshot.toObjects(CustomerDto::class.java)
    }

    suspend fun fetchCustomerById(id: String): CustomerDto {
        val documentSnapshot = tenantContext.getCustomerCollection().document(id).get().await()
        return documentSnapshot.toObject(CustomerDto::class.java)
            ?: throw Exception("Customer with ID $id not found")
    }

    suspend fun addCustomer(customerEntity: CustomerEntity) {
        tenantContext.getCustomerCollection().document(customerEntity.uuid).set(customerEntity).await()
    }

    suspend fun updateCustomer(updatedCustomer: CustomerEntity) {
        tenantContext.getCustomerCollection().document(updatedCustomer.uuid).set(updatedCustomer).await()
    }

    // ------------------ Invoice Collection Operations ------------------

    suspend fun fetchInvoiceList(): List<InvoiceDto> {
        val snapshot = tenantContext.getInvoiceCollection().get().await()
        return snapshot.toObjects(InvoiceDto::class.java)
    }

    suspend fun fetchInvoiceById(id: String): InvoiceDto {
        val documentSnapshot = tenantContext.getInvoiceCollection().document(id).get().await()
        return documentSnapshot.toObject(InvoiceDto::class.java)
            ?: throw Exception("Invoice with ID $id not found")
    }

    suspend fun addInvoice(invoiceEntity: InvoiceEntity) {
        tenantContext.getInvoiceCollection().document(invoiceEntity.uuid).set(invoiceEntity).await()
    }

    suspend fun updateInvoice(updatedInvoice: InvoiceEntity) {
        tenantContext.getInvoiceCollection().document(updatedInvoice.uuid).set(updatedInvoice).await()
    }
}
