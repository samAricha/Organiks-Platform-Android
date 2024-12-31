package teka.android.organiks_platform_android.data.room

class RoomRepository(
    private val customerDao: CustomerDao,
    private val fruitCollectionDao: FruitCollectionDao,
    private val invoiceDao: InvoiceDao
) {

    // Room operations
    suspend fun getAllCustomers() = customerDao.getAllCustomers()
    suspend fun getUnsyncedCustomers() = customerDao.getUnsyncedCustomers()
    suspend fun markCustomerAsSynced(uuid: String) = customerDao.markCustomerAsSynced(uuid)

    suspend fun getAllFruitCollections() = fruitCollectionDao.getAllFruitCollections()
    suspend fun getUnsyncedFruitCollections() = fruitCollectionDao.getUnsyncedFruitCollections()
    suspend fun markFruitCollectionAsSynced(uuid: String) = fruitCollectionDao.markFruitCollectionAsSynced(uuid)

    suspend fun getAllInvoices() = invoiceDao.getAllInvoices()
    suspend fun getUnsyncedInvoices() = invoiceDao.getUnsyncedInvoices()
    suspend fun markInvoiceAsSynced(uuid: String) = invoiceDao.markInvoiceAsSynced(uuid)
}
