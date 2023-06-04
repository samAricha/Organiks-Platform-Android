package teka.android.organiks_platform_android.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class OrganiksDatabase: RoomDatabase() {

    abstract fun eggTypeDao():EggTypeDao
    abstract fun eggCollectionDao():EggCollectionDao

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