package teka.android.organiks_platform_android.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity
import teka.android.organiks_platform_android.data.room.entities.EggCollection
import teka.android.organiks_platform_android.data.room.entities.EggType
import teka.android.organiks_platform_android.data.room.entities.ExpenditureEntity
import teka.android.organiks_platform_android.data.room.entities.FarmEntity
import teka.android.organiks_platform_android.data.room.entities.FruitCollectionEntity
import teka.android.organiks_platform_android.data.room.entities.FruitType
import teka.android.organiks_platform_android.data.room.entities.InvoiceEntity
import teka.android.organiks_platform_android.data.room.entities.MilkCollection
import teka.android.organiks_platform_android.data.room.entities.ProductionCategory

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

    @Query("SELECT * FROM fruit_collections WHERE uuid=:uuid")
    fun getFruitCollectionByUUId(uuid:String): Flow<FruitCollectionEntity>


    @Query("""
        SELECT * FROM egg_collections AS EC INNER JOIN egg_types AS ET ON
        EC.eggTypeId = ET.egg_type_id
    """)
    fun getFruitCollectionsWithFruitTypes():Flow<List<EggTypeEggCollectionItem>>

}

@Entity
@Dao
interface CustomerDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: CustomerEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(customer: CustomerEntity)

    @Delete
    suspend fun delete(customer: CustomerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: List<CustomerEntity>)

    @Query("SELECT * FROM customer_table ORDER BY date DESC")
    fun getAllCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customer_table WHERE customer_id=:customerId")
    fun getCustomersByCustomerId(customerId:Int): Flow<CustomerEntity>

}

@Entity
@Dao
interface InvoiceDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(invoice: InvoiceEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(invoice: InvoiceEntity)

    @Delete
    suspend fun delete(invoice: InvoiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoiceList: List<InvoiceEntity>)

    @Query("SELECT * FROM invoice_table ORDER BY date DESC")
    fun getAllInvoices(): Flow<List<InvoiceEntity>>

    @Query("SELECT * FROM invoice_table WHERE uuid=:id")
    fun getInvoicesByUUId(id:String): Flow<InvoiceEntity>

}

@Entity
@Dao
interface FarmDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(farm: FarmEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(farm: FarmEntity)

    @Delete
    suspend fun delete(farm: FarmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarm(farm: List<FarmEntity>)

    @Query("SELECT * FROM farm_table ORDER BY date DESC")
    fun getAllFarms(): Flow<List<FarmEntity>>

    @Query("SELECT * FROM farm_table WHERE farm_id=:farmId")
    fun getFarmsByFarmId(farmId:Int): Flow<FarmEntity>
}

@Entity
@Dao
interface ExpenditureDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenditure: ExpenditureEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(expenditure: ExpenditureEntity)

    @Delete
    suspend fun delete(expenditure: ExpenditureEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenditure(farm: List<ExpenditureEntity>)

    @Query("SELECT * FROM expenditure_table ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenditureEntity>>

    @Query("SELECT * FROM expenditure_table WHERE expenditure_id=:expenditureId")
    fun getExpenditureById(expenditureId:Int): Flow<ExpenditureEntity>
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