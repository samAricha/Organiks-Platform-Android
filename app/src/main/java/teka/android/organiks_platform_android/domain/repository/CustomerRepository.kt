package teka.android.organiks_platform_android.domain.repository

import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.util.Resource

interface CustomerRepository {
    fun getFruitCustomers(): Flow<Resource<List<CustomerEntity>>>
    fun getCustomerById(caseId: String): Flow<Resource<CustomerEntity>>
    fun createCustomers(case: CustomerEntity): Flow<Resource<Unit>>
    fun syncCustomers(): Flow<Resource<Unit>>
}