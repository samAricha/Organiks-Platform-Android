package teka.android.organiks_platform_android.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "production_categories")
data class ProductionCategory(
    @ColumnInfo(name = "production_category_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String,
)

@Entity(tableName = "egg_types")
data class EggType(
    @ColumnInfo(name = "egg_type_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String
)

@Entity(tableName = "fruit_types")
data class FruitType(
    @ColumnInfo(name = "fruit_type_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String

)

@Entity(tableName = "egg_collections")
data class EggCollection(
    @ColumnInfo(name = "egg_collection_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String = generateUniqueId(),
    val qty: String,
    val cracked: String,
    val eggTypeId: Int,
    val date: Long,
    var isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}



@Entity(tableName = "milk_collections")
data class MilkCollection(
    @ColumnInfo(name = "milk_collection_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String = generateUniqueId(),
    val qty:String,
    val date: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    var isBackedUp: Boolean = false,
    ){
    companion object {
        private fun generateUniqueId(): String {
            // Use UUID for generating a unique ID
            return UUID.randomUUID().toString()
        }
    }
}


@Entity(tableName = "fruit_collections")
data class FruitCollectionEntity(
    @ColumnInfo(name = "fruit_collection_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String = generateUniqueId(),
    val qty: String,
    val fruitTypeId: Int,
    val date: Long,
    var isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}




