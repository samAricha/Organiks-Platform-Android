package teka.android.organiks_platform_android.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.data.room.models.ProductionCategory


@Dao
interface EggCollectionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eggCollection: EggCollection)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(eggCollection: EggCollection)

    @Delete
    suspend fun delete(eggCollection: EggCollection)


    @Query("SELECT * FROM egg_collections")
    fun getAllEggCollections(): Flow<List<EggCollection>>

    @Query("SELECT * FROM egg_collections WHERE egg_collection_id=:collectionId")
    fun getEggCollectionById(collectionId:Int): Flow<EggCollection>


    @Query("""
        SELECT * FROM egg_collections AS EC INNER JOIN egg_types AS ET ON
        EC.eggTypeId = ET.egg_type_id
    """)
    fun getEggCollectionsWithEggTypes():Flow<List<EggTypeEggCollectionItem>>






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

@Entity
@Dao
interface ProductionCategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCategory(productionCategory: ProductionCategory)

    @Query("SELECT * FROM production_categories")
    fun getProductionCategories(): Flow<List<ProductionCategory>>

    @Query("SELECT * FROM production_categories WHERE production_category_id=:categoryId")
    fun getProductionCategoryById(categoryId:Int):Flow<ProductionCategory>

}



data class EggTypeEggCollectionItem(
    @Embedded val eggType: EggType,
    @Embedded val eggCollection: EggCollection,
)