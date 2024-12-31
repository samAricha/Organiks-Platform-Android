package teka.android.organiks_platform_android.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import teka.android.organiks_platform_android.data.remote.firebase.TenantContext
import teka.android.organiks_platform_android.data.repositoryImpl.CustomerRepositoryImpl
import teka.android.organiks_platform_android.data.repositoryImpl.FruitCollectionsRepositoryImpl
import teka.android.organiks_platform_android.data.repositoryImpl.InvoiceRepositoryImpl
import teka.android.organiks_platform_android.data.room.RoomRepository
import teka.android.organiks_platform_android.domain.repository.def.CustomerRepository
import teka.android.organiks_platform_android.domain.repository.def.FruitCollectionRepository
import teka.android.organiks_platform_android.domain.repository.def.InvoiceRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    @Named("userId")
    fun provideUserId(auth: FirebaseAuth): String {
        return auth.currentUser?.uid ?: throw Exception("User is not logged in")
    }


    @Singleton
    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideTenantContext(auth: FirebaseAuth): TenantContext {
        return TenantContext(auth)
    }

    @Singleton
    @Provides
    fun provideRemoteDatabase(
        tenantContext: TenantContext
    ): FirebaseRemoteDatabase {
        return FirebaseRemoteDatabase(tenantContext)
    }


    @Singleton
    @Provides
    fun provideFruitCollectionRepository(
        firebaseRemoteDatabase1: FirebaseRemoteDatabase,
        roomRepository: RoomRepository,
    ): FruitCollectionRepository =
        FruitCollectionsRepositoryImpl(
            firebaseRemoteDatabase1 = firebaseRemoteDatabase1,
            roomRepository = roomRepository
        )


    @Singleton
    @Provides
    fun provideCustomerRepository(
        firebaseRemoteDatabase1: FirebaseRemoteDatabase,
        roomRepository: RoomRepository
    ): CustomerRepository =
        CustomerRepositoryImpl(
            firebaseRemoteDatabase1 = firebaseRemoteDatabase1,
            roomRepository = roomRepository
        )


    @Singleton
    @Provides
    fun provideInvoiceRepository(
        firebaseRemoteDatabase1: FirebaseRemoteDatabase,
        roomRepository: RoomRepository
    ): InvoiceRepository =
        InvoiceRepositoryImpl(
            firebaseRemoteDatabase1 = firebaseRemoteDatabase1,
            roomRepository = roomRepository
        )

}
