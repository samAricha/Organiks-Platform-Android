package teka.android.organiks_platform_android.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import teka.android.organiks_platform_android.data.room.OrganiksDatabase
import teka.android.organiks_platform_android.data.room_remote_sync.RemoteDataUpdater
import teka.android.organiks_platform_android.repository.DataStoreRepository
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.networking.NetworkConnectivityObserver
import teka.android.organiks_platform_android.repository.DbRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideOrganiksDatabase(@ApplicationContext context: Context): OrganiksDatabase {
        return Room.databaseBuilder(
            context,
            OrganiksDatabase::class.java,
            "organiks_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepository(database: OrganiksDatabase): DbRepository {
        return DbRepository(
            eggTypeDao = database.eggTypeDao(),
            eggCollectionDao = database.eggCollectionDao(),
            productionCategoryDao = database.productionCategoryDao(),
            milkCollectionDao = database.milkCollectionDao(),
            fruitCollectionDao = database.fruitCollectionDao()
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun provideSplashViewModel(repository: DataStoreRepository): SplashViewModel {
        return SplashViewModel(repository)
    }

    @Singleton
    @Provides
    fun provideRemoteDataUpdater(@ApplicationContext context: Context): RemoteDataUpdater {
        return RemoteDataUpdater(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}