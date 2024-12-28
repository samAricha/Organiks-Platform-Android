package teka.android.organiks_platform_android.data.repositoryImpl

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.models.toCustomerEntity
import teka.android.organiks_platform_android.data.remote.models.toCustomerEntityList
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.domain.repository.CustomerRepository
import teka.android.organiks_platform_android.util.Resource


class CustomerRepositoryImpl @Inject constructor(
    private val firebaseRemoteDatabase: FirebaseRemoteDatabase,
) : CustomerRepository {

    override fun getFruitCustomers(): Flow<Resource<List<CustomerEntity>>> =  flow{
        emit(Resource.Loading())
        try {
            val caseList = firebaseRemoteDatabase.fetchCustomerList()
            emit(Resource.Success(caseList.toCustomerEntityList()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching churches"))
        }
    }

    override fun getCustomerById(id: String): Flow<Resource<CustomerEntity>> = flow{
        emit(Resource.Loading())
        try {
            val customer = firebaseRemoteDatabase.fetchCustomerById(id)
            emit(Resource.Success(customer.toCustomerEntity()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching customer with ID: $id"))
        }    }

    override fun createCustomers(customer: CustomerEntity): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        try {
            firebaseRemoteDatabase.addCustomer(customer)
            emit(Resource.Success(Unit)) // Indicate success with no specific data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error creating customer"))
        }
    }

}