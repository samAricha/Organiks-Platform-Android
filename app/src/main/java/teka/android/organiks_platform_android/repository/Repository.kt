package teka.android.organiks_platform_android.repository

import teka.android.organiks_platform_android.data.room.EggCollectionDao
import teka.android.organiks_platform_android.data.room.EggTypeDao
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType

class Repository(
    private val eggTypeDao: EggTypeDao,
    private val eggCollectionDao: EggCollectionDao,
) {
    //the following are methods which are going to help us get our data.

    val eggTypes = eggTypeDao.getAllEggTypes()
    val getEggCollections = eggCollectionDao.getAllEggCollections()

    //the following are functions that are going to help us insert/save our data

    suspend fun insertEggCollection(eggCollection: EggCollection){
        eggCollectionDao.insert(eggCollection)
    }
    suspend fun insertEggType(eggType: EggType){

        eggTypeDao.insert(eggType)

    }


    suspend fun updateEggCollection(eggCollection: EggCollection){

        eggCollectionDao.update(eggCollection = eggCollection)

    }

    //the following are functions that are going to help us delete/erase data

    suspend fun deleteCollection(eggCollection: EggCollection){

        eggCollectionDao.delete(eggCollection = eggCollection)

    }
}