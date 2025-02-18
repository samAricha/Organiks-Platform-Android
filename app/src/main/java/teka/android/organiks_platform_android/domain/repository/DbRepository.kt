package teka.android.organiks_platform_android.domain.repository

import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.CustomerDao
import teka.android.organiks_platform_android.data.room.EggCollectionDao
import teka.android.organiks_platform_android.data.room.EggTypeDao
import teka.android.organiks_platform_android.data.room.ExpenditureDao
import teka.android.organiks_platform_android.data.room.FarmDao
import teka.android.organiks_platform_android.data.room.FruitCollectionDao
import teka.android.organiks_platform_android.data.room.InvoiceDao
import teka.android.organiks_platform_android.data.room.MilkCollectionDao
import teka.android.organiks_platform_android.data.room.ProductionCategoryDao
import teka.android.organiks_platform_android.data.room.WeightsDao
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.EggCollection
import teka.android.organiks_platform_android.data.room.entities.EggType
import teka.android.organiks_platform_android.data.room.entities.ExpenditureEntity
import teka.android.organiks_platform_android.data.room.entities.FarmEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.data.room.entities.MilkCollection
import teka.android.organiks_platform_android.data.room.entities.ProductionCategory
import teka.android.organiks_platform_android.data.room.entities.WeighingEntity

class DbRepository(
    private val eggTypeDao: EggTypeDao,
    private val eggCollectionDao: EggCollectionDao,
    private val fruitCollectionDao: FruitCollectionDao,
    private val weightsDao: WeightsDao,
    private val customerDao: CustomerDao,
    private val invoiceDao: InvoiceDao,
    private val farmDao: FarmDao,
    private val expenditureDao: ExpenditureDao,
    private val milkCollectionDao: MilkCollectionDao,
    private val productionCategoryDao: ProductionCategoryDao
) {
    //the following are methods which are going to help us get our data.

    val getProductionCategories = productionCategoryDao.getProductionCategories()

    val eggTypes = eggTypeDao.getAllEggTypes()
    val getEggCollections = eggCollectionDao.getAllEggCollections()
    val getMilkCollection = milkCollectionDao.getAllMilkCollections()
    val getFruitCollections = fruitCollectionDao.getAllFruitCollections()
    val getWeights = weightsDao.getAllWeights()
    val getCustomers = customerDao.getAllCustomers()
    val getAllInvoices = invoiceDao.getAllInvoices()
    val getAllExpenses = expenditureDao.getAllExpenses()
    val getMyFarmsList = farmDao.getAllFarms()
    val getEggCollectionsWithEggTypes = eggCollectionDao.getEggCollectionsWithEggTypes()



    //getting our data while filtering it
    fun getEggCollectionById(id: Int) = eggCollectionDao
        .getEggCollectionById(id)

    fun getEggTypeById(id: Int) = eggTypeDao
        .getEggType(id)


    //the following are functions that are going to help us insert/save our data
    suspend fun insertProductionCategory(productionCategory: ProductionCategory){
        productionCategoryDao.insertProductionCategory(productionCategory = productionCategory)
    }
    suspend fun insertEggCollection(eggCollection: EggCollection){
        eggCollectionDao.insert(eggCollection)
    }
    suspend fun insertEggType(eggType: EggType){
        eggTypeDao.insert(eggType)
    }


    //the following are functions that are going to help us update our data
    suspend fun updateEggCollection(eggCollection: EggCollection){
        eggCollectionDao.update(eggCollection = eggCollection)
    }

    //the following are functions that are going to help us delete/erase data
    suspend fun deleteEggCollection(eggCollection: EggCollection){
        eggCollectionDao.delete(eggCollection = eggCollection)
    }
    suspend fun saveRemoteEggCollections(eggCollections: List<EggCollection>){
        eggCollectionDao.insertEggCollections(eggCollections)
    }

    //<<<<<<<<<< FRUIT COLLECTIONS >>>>>>>>
    suspend fun insertFruitCollection(fruitCollection: FruitCollectionEntity){
        fruitCollectionDao.insert(fruitCollection)
    }
    suspend fun updateFruitCollection(fruitCollection: FruitCollectionEntity){
        fruitCollectionDao.update(fruitCollection = fruitCollection)
    }
    fun getFruitCollectionByUUId(uuid: String): Flow<FruitCollectionEntity> {
        return fruitCollectionDao.getFruitCollectionByUUId(uuid)
    }
    
    //<<<<<<<<<< WEIGHTS >>>>>>>>
    suspend fun insertWeight(weight: WeighingEntity){
        weightsDao.insert(weight)
    }
    suspend fun updateWeight(weighingEntity: WeighingEntity){
        weightsDao.update(weighingEntity = weighingEntity)
    }
    fun getWeightsByFruitCollectionId(uuid: String): Flow<WeighingEntity> {
        return weightsDao.getWeightsByFruitCollectionId(uuid)
    }

    //<<<<<<<<<< CUSTOMER COLLECTIONS >>>>>>>>
    suspend fun insertCustomerEntity(customer: CustomerEntity){
        customerDao.insert(customer)
    }
    suspend fun updateCustomerEntity(customerEntity: CustomerEntity){
        customerDao.update(customerEntity)
    }

    //<<<<<<<<<< INVOICE COLLECTIONS >>>>>>>>
    suspend fun insertInvoiceEntity(invoice: InvoiceEntity){
        invoiceDao.insert(invoice)
    }

    fun getInvoiceByUUId(uuid: String): Flow<InvoiceEntity> {
        return invoiceDao.getInvoicesByUUId(uuid)
    }


    //<<<<<<<<<< FARM COLLECTIONS >>>>>>>>
    suspend fun insertFarmEntity(farm: FarmEntity){
        farmDao.insert(farm)
    }
    suspend fun updateFarmEntity(customerEntity: FarmEntity){
        farmDao.update(customerEntity)
    }

    //<<<<<<<<<< EXPENDITURE COLLECTIONS >>>>>>>>
    suspend fun insertExpenditureEntity(expenditure: ExpenditureEntity){
        expenditureDao.insert(expenditure)
    }
    suspend fun updateExpenditureEntity(expenditure: ExpenditureEntity){
        expenditureDao.update(expenditure)
    }

    //<<<<<<<<<< MILK COLLECTIONS >>>>>>>>
    suspend fun saveRemoteMilkCollections(milkCollections: List<MilkCollection>){
        milkCollectionDao.insertMilkCollections(milkCollections)
    }
    suspend fun updateMilkCollection(milkCollection: MilkCollection){
        milkCollectionDao.update(milkCollection = milkCollection)
    }
    suspend fun insertMilkCollection(milkCollection: MilkCollection){
        milkCollectionDao.insert(milkCollection)
    }
}