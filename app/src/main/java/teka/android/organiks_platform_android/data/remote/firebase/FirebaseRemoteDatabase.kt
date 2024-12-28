package teka.android.organiks_platform_android.data.remote.firebase

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import teka.android.organiks_platform_android.data.remote.models.CustomerDto
import teka.android.organiks_platform_android.data.remote.models.FruitCollectionDto
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity


class FirebaseRemoteDatabase(
    private val fruitCollection: CollectionReference,
    private val customerCollection: CollectionReference
) {
    suspend fun fetchFruitCollectionList(): List<FruitCollectionDto> {
        val snapshot = fruitCollection.get().await()
        return snapshot.toObjects(FruitCollectionDto::class.java)
    }


    suspend fun fetchFruitCollectionById(fruitCollectionId: String): FruitCollectionDto {
        val caseSnapshot = fruitCollection.document(fruitCollectionId).get().await()
        return caseSnapshot.toObject(FruitCollectionDto::class.java)
            ?: throw Exception("Fruit Colleection with ID $fruitCollectionId not found")
    }


    suspend fun addFruitCollection(fruitCollection: FruitCollectionEntity) {
        this.fruitCollection.document(fruitCollection.uuid).set(fruitCollection).await()
    }

    suspend fun updateFruitCollection(updatedFruitCollection: FruitCollectionDto) {
        fruitCollection.document(updatedFruitCollection.uuid).set(updatedFruitCollection).await()
    }


    suspend fun fetchCustomerList(): List<CustomerDto> {
        val snapshot = customerCollection.get().await()
        return snapshot.toObjects(CustomerDto::class.java)
    }


    suspend fun fetchCustomerById(id: String): CustomerDto {
        val caseSnapshot = customerCollection.document(id).get().await()
        return caseSnapshot.toObject(CustomerDto::class.java)
            ?: throw Exception("Customer with ID $id not found")
    }


    suspend fun addCustomer(customerEntity: CustomerEntity) {
        this.customerCollection.document(customerEntity.uuid).set(customerEntity).await()
    }

    suspend fun updateCustomer(updatedCustomer: CustomerEntity) {
        customerCollection.document(updatedCustomer.uuid).set(updatedCustomer).await()
    }

}