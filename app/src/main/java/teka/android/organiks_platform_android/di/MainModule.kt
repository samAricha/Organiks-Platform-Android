package teka.android.organiks_platform_android.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import teka.android.organiks_platform_android.data.remote.retrofit.RetrofitProvider
import teka.android.organiks_platform_android.data.room.OrganiksDatabase
import teka.android.organiks_platform_android.data.room_remote_sync.RemoteDataUpdater
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.MessageDao
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.MessageDatabase
import teka.android.organiks_platform_android.domain.repository.DataStoreRepository
import teka.android.organiks_platform_android.presentation.feature_splash_screen.presentation.SplashViewModel
import teka.android.organiks_platform_android.networking.NetworkConnectivityObserver
import teka.android.organiks_platform_android.domain.repository.DbRepository
import teka.android.organiks_platform_android.domain.repository.RemoteEggRecordsRepository
import teka.android.organiks_platform_android.domain.repository.RemoteFruitRecordsRepository
import teka.android.organiks_platform_android.domain.repository.RemoteMilkRecordsRepository
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.FirebaseAuthRepository
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.FirebaseAuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext appContext: Context
    ): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository = impl

    @Singleton
    @Provides
    fun provideOrganiksDatabase(
        @ApplicationContext context: Context
    ): OrganiksDatabase {
        return Room.databaseBuilder(
            context,
            OrganiksDatabase::class.java,
            "organiks_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideGeminiDatabase(
        @ApplicationContext context: Context
    ): MessageDatabase {
        return Room.databaseBuilder(
            context,
            MessageDatabase::class.java,
            "message.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideGeminiMessageDao(
        db: MessageDatabase
    ): MessageDao {
        return db.dao
    }

    @Singleton
    @Provides
    fun provideRepository(
        database: OrganiksDatabase
    ): DbRepository {
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
    fun provideSplashViewModel(
        repository: DataStoreRepository
    ): SplashViewModel {
        return SplashViewModel(repository)
    }

    @Singleton
    @Provides
    fun provideRemoteDataUpdater(
        @ApplicationContext context: Context
    ): RemoteDataUpdater {
        return RemoteDataUpdater(context)
    }

    @Singleton
    @Provides
    fun provideRemoteEggRecordsRepository(
        @ApplicationContext context: Context
    ): RemoteEggRecordsRepository {
        val eggRecordsRepository = RemoteEggRecordsRepository(
            RetrofitProvider.createEggCollectionService(),

        )
        return eggRecordsRepository
    }

    @Singleton
    @Provides
    fun provideRemoteMilkRecordsRepository(
        @ApplicationContext context: Context
    ): RemoteMilkRecordsRepository {
        val milkRecordsRepository = RemoteMilkRecordsRepository(
            RetrofitProvider.createMilkCollectionService(),

            )
        return milkRecordsRepository
    }

    @Singleton
    @Provides
    fun provideRemoteFruitsRecordsRepository(
        @ApplicationContext context: Context
    ): RemoteFruitRecordsRepository {
        val fruitsRecordsRepository = RemoteFruitRecordsRepository(
            RetrofitProvider.createFruitCollectionService(),

            )
        return fruitsRecordsRepository
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}