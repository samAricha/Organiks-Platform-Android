package teka.android.organiks_platform_android.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.data.room.models.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.models.FruitType
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.data.room.models.ProductionCategory

@Entity
@Dao
interface EggCollectionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eggCollection: EggCollection)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(eggCollection: EggCollection)

    @Delete
    suspend fun delete(eggCollection: EggCollection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEggCollections(eggCollections: List<EggCollection>)


    @Query("SELECT * FROM egg_collections ORDER BY date DESC")
    fun getAllEggCollections(): Flow<List<EggCollection>>

    @Query("SELECT * FROM egg_collections WHERE egg_collection_id=:collectionId")
    fun getEggCollectionById(collectionId:Int): Flow<EggCollection>


    @Query("""
        SELECT * FROM egg_collections AS EC INNER JOIN egg_types AS ET ON
        EC.eggTypeId = ET.egg_type_id
    """)
    fun getEggCollectionsWithEggTypes():Flow<List<EggTypeEggCollectionItem>>

}

@Entity
@Dao
interface FruitCollectionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fruitCollection: FruitCollectionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(fruitCollection: FruitCollectionEntity)

    @Delete
    suspend fun delete(fruitCollection: FruitCollectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFruitCollections(fruitCollections: List<FruitCollectionEntity>)


    @Query("SELECT * FROM fruit_collections ORDER BY date DESC")
    fun getAllFruitCollections(): Flow<List<FruitCollectionEntity>>

    @Query("SELECT * FROM fruit_collections WHERE fruit_collection_id=:collectionId")
    fun getFruitCollectionById(collectionId:Int): Flow<FruitCollectionEntity>


    @Query("""
        SELECT * FROM egg_collections AS EC INNER JOIN egg_types AS ET ON
        EC.eggTypeId = ET.egg_type_id
    """)
    fun getFruitCollectionsWithFruitTypes():Flow<List<EggTypeEggCollectionItem>>

}


@Entity
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
interface FruitTypeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fruitType: FruitType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(fruitType: FruitType)

    @Delete
    suspend fun delete(fruitType: FruitType)

    @Query("SELECT * FROM fruit_types")
    fun getAllEggTypes(): Flow<List<FruitType>>

    @Query("SELECT * FROM fruit_types WHERE fruit_type_id=:fruitTypeId")
    fun getEggType(fruitTypeId:Int): Flow<FruitType>

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
data class FruitTypeFruitCollectionItem(
    @Embedded val fruitType: FruitType,
    @Embedded val fruitCollection: FruitCollectionEntity,
)



@Entity
@Dao
interface MilkCollectionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(milkCollection: MilkCollection)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(milkCollection: MilkCollection)

    @Delete
    suspend fun delete(milkCollection: MilkCollection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilkCollections(milkCollections: List<MilkCollection>)


    @Query("SELECT * FROM milk_collections ORDER BY date DESC")
    fun getAllMilkCollections(): Flow<List<MilkCollection>>

    @Query("SELECT * FROM milk_collections WHERE milk_collection_id=:collectionId")
    fun getMilkCollectionById(collectionId:Int): Flow<MilkCollection>
}