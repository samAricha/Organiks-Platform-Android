package teka.android.organiks_platform_android.data.generics

open class GenericRepository<T>(
    private val genericDao: BaseDao<T>,
) {
    // Get all items
//    fun getAllItems(): List<T> {
//        val query = SimpleSQLiteQuery("SELECT * FROM $tableName")
////        getItemsFromTable(tableName)
//        return genericDao.getItemsWithDynamicTable(query)
//    }

    // Insert an item
    suspend fun insertItem(item: T) {
        genericDao.insert(item)
    }

    suspend fun insertAllItems(items: List<T>) {
        genericDao.insertAll(items)
    }

    // Update an item
    suspend fun updateItem(item: T) {
        genericDao.update(item)
    }

    // Delete an item
    suspend fun deleteItem(item: T) {
        genericDao.delete(item)
    }
}
