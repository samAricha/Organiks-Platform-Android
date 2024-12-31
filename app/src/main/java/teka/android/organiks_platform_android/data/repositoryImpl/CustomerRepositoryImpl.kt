package teka.android.organiks_platform_android.data.repositoryImpl

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.models.toCustomerEntity
import teka.android.organiks_platform_android.data.remote.models.toCustomerEntityList
import teka.android.organiks_platform_android.data.room.RoomRepository
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.domain.repository.def.CustomerRepository
import teka.android.organiks_platform_android.util.Resource


class CustomerRepositoryImpl @Inject constructor(
    private val roomRepository: RoomRepository,
    private val firebaseRemoteDatabase1: FirebaseRemoteDatabase,
) : CustomerRepository {

    // Sync customers from Room to Firebase
    override fun syncCustomers(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading()) // Emit loading state before syncing

        try {
            // Fetch all unsynced customers from Room database
            val unsyncedCustomers = roomRepository.getUnsyncedCustomers()

            // Iterate over the unsynced customers and sync them to Firebase
            for (customer in unsyncedCustomers) {
                // Add customer to Firebase
                firebaseRemoteDatabase1.addCustomer(customer)
                // After successful sync, mark the customer as synced in Room
                roomRepository.markCustomerAsSynced(customer.uuid)
            }

            emit(Resource.Success(Unit)) // Emit success after syncing all customers
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error syncing customers")) // Emit error if something goes wrong
        }
    }

    override fun getFruitCustomers(): Flow<Resource<List<CustomerEntity>>> =  flow{
        emit(Resource.Loading())
        try {
            val caseList = firebaseRemoteDatabase1.fetchCustomerList()
            emit(Resource.Success(caseList.toCustomerEntityList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching churches"))
        }
    }

    override fun getCustomerById(id: String): Flow<Resource<CustomerEntity>> = flow{
        emit(Resource.Loading())
        try {
            val customer = firebaseRemoteDatabase1.fetchCustomerById(id)
            emit(Resource.Success(customer.toCustomerEntity()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching customer with ID: $id"))
        }
    }

    override fun createCustomers(customer: CustomerEntity): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            firebaseRemoteDatabase1.addCustomer(customer)
            emit(Resource.Success(Unit)) // Indicate success with no specific data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error creating customer"))
        }
    }

}