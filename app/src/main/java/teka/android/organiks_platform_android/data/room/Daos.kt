package teka.android.organiks_platform_android.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType


@Dao
interface ItemDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eggCollection: EggCollection)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(eggCollection: EggCollection)

    @Delete
    suspend fun delete(eggCollection: EggCollection)


    @Query("SELECT * FROM egg_collections")
    fun getAllEggCollections(): Flow<List<EggCollection>>

    @Query("SELECT * FROM egg_collections WHERE collection_id=:collectionId")
    fun getEggCollection(collectionId:Int): Flow<EggCollection>

}


@Dao
interface EggTypeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eggType: EggType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(eggType: EggType)

    @Delete
    suspend fun delete(eggType: EggType)

    @Query("SELECT * FROM egg_types")
    fun getAllEggTypes(): Flow<List<EggType>>

    @Query("SELECT * FROM egg_types WHERE egg_type_id=:eggTypeId")
    fun getEggType(eggTypeId:Int): Flow<EggType>

}



data class EggFarmProduction(
    @Embedded val eggType: EggType,
    @Embedded val eggCollection: EggCollection,
)