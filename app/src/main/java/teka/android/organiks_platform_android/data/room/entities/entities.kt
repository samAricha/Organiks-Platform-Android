package teka.android.organiks_platform_android.data.room.entities


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
    val fruitTypeId: String,
    val date: Long,
    val time: String,
    var isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }

    val searchableString: String
        get() = "${fruitTypeId} ${qty}"

}

@Entity(tableName = "customer_table")
data class CustomerEntity(
    @ColumnInfo(name = "customer_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String = generateUniqueId(),
    val name: String,
    val phone: String,
    val email: String,
    val product: String,
    val date: Long,
    val time: String,
    var isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }

    val searchableString: String
        get() = "${name} ${phone}"
}

@Entity(tableName = "invoice_table")
data class InvoiceEntity(
    @ColumnInfo(name = "invoice_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String = generateUniqueId(),
    val toName: String,
    val toPhone: String,
    val fromName: String,
    val fromPhone: String,
    val totalAmount: String,
    val fruitCollection: String,
    val date: Long,
    val time: String,
    var isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}


