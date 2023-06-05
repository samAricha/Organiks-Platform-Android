package teka.android.organiks_platform_android.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import teka.android.organiks_platform_android.data.room.converters.DateConverter
import teka.android.organiks_platform_android.data.room.models.EggCollection
import teka.android.organiks_platform_android.data.room.models.EggType
import teka.android.organiks_platform_android.data.room.models.MilkCollection
import teka.android.organiks_platform_android.data.room.models.ProductionCategory

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [ProductionCategory::class, EggType::class, EggCollection::class, MilkCollection::class],
    version = 1,
    exportSchema = false
)
abstract class OrganiksDatabase: RoomDatabase() {

    abstract fun eggTypeDao():EggTypeDao
    abstract fun eggCollectionDao():EggCollectionDao
    abstract fun productionCategoryDao():ProductionCategoryDao

    companion object{
        @Volatile//this is creating the db in a thread safe manner
        var INSTANCE: OrganiksDatabase? = null
        fun getDatabase(context: Context): OrganiksDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    OrganiksDatabase::class.java,
                    "organiks_db"
                ).build()
                INSTANCE= instance
                return instance
            }

        }
    }

}