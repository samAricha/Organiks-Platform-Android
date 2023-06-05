package teka.android.organiks_platform_android.di

import android.content.Context
import teka.android.organiks_platform_android.data.room.OrganiksDatabase
import teka.android.organiks_platform_android.repository.Repository

object OrganiksDI {

    lateinit var db: OrganiksDatabase
        private set

    val repository by lazy {
        Repository(
            eggTypeDao = db.eggTypeDao(),
            eggCollectionDao = db.eggCollectionDao(),
            productionCategoryDao = db.productionCategoryDao()
        )
    }

    fun provideDb(context: Context){
        db = OrganiksDatabase.getDatabase(context)
    }
}