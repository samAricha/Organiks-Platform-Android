package teka.android.organiks_platform_android.repository

import teka.android.organiks_platform_android.data.room.EggCollectionDao
import teka.android.organiks_platform_android.data.room.EggTypeDao
import teka.android.organiks_platform_android.data.room.MilkCollectionDao
import teka.android.organiks_platform_android.data.room.ProductionCategoryDao
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.data.room.models.ProductionCategory

class DbRepository(
    private val eggTypeDao: EggTypeDao,
    private val eggCollectionDao: EggCollectionDao,
    private val milkCollectionDao: MilkCollectionDao,
    private val productionCategoryDao: ProductionCategoryDao
) {
    //the following are methods which are going to help us get our data.

    val getProductionCategories = productionCategoryDao.getProductionCategories()

    val eggTypes = eggTypeDao.getAllEggTypes()
    val getEggCollections = eggCollectionDao.getAllEggCollections()
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
    suspend fun updateMilkCollection(milkCollection: MilkCollection){
        milkCollectionDao.update(milkCollection = milkCollection)
    }
    //the following are functions that are going to help us delete/erase data
    suspend fun deleteCollection(eggCollection: EggCollection){
        eggCollectionDao.delete(eggCollection = eggCollection)
    }
    suspend fun saveRemoteEggCollections(eggCollections: List<EggCollection>){
        eggCollectionDao.insertEggCollections(eggCollections)
    }


    //<<<<<<<<<< MILK COLLECTIONS >>>>>>>>
    suspend fun saveRemoteMilkCollections(milkCollections: List<MilkCollection>){
        milkCollectionDao.insertMilkCollections(milkCollections)
    }
}