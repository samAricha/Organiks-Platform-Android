package teka.android.organiks_platform_android.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseConstants
import teka.android.organiks_platform_android.data.remote.firebase.FirebaseRemoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import teka.android.organiks_platform_android.data.repositoryImpl.CustomerRepositoryImpl
import teka.android.organiks_platform_android.data.repositoryImpl.FruitCollectionsRepositoryImpl
import teka.android.organiks_platform_android.data.repositoryImpl.InvoiceRepositoryImpl
import teka.android.organiks_platform_android.domain.repository.CustomerRepository
import teka.android.organiks_platform_android.domain.repository.FruitCollectionRepository
import teka.android.organiks_platform_android.domain.repository.InvoiceRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

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
    @Named("fruitCollection")
    fun provideFruitCollection(): CollectionReference {
        return FirebaseFirestore
            .getInstance()
            .collection(FirebaseConstants.FRUIT_COLLECTIONS)
    }


    @Singleton
    @Provides
    @Named("customerCollection")
    fun provideCustomerCollection(): CollectionReference {
        return FirebaseFirestore
            .getInstance()
            .collection(FirebaseConstants.CUSTOMER_COLLECTION)
    }


    @Singleton
    @Provides
    fun provideMusicDatabase(
        @Named("fruitCollection") fruitCollection: CollectionReference,
        @Named("customerCollection") customerCollection: CollectionReference,
        @Named("invoiceCollection") invoiceCollection: CollectionReference
    ): FirebaseRemoteDatabase =
        FirebaseRemoteDatabase(
            fruitCollection,
            customerCollection,
            invoiceCollection
        )


    @Singleton
    @Provides
    fun provideFruitCollectionRepository(
        firebaseRemoteDatabase: FirebaseRemoteDatabase,
    ): FruitCollectionRepository =
        FruitCollectionsRepositoryImpl(firebaseRemoteDatabase)


    @Singleton
    @Provides
    fun provideCustomerRepository(
        firebaseRemoteDatabase: FirebaseRemoteDatabase,
    ): CustomerRepository =
        CustomerRepositoryImpl(firebaseRemoteDatabase)


    @Singleton
    @Provides
    fun provideInvoiceRepository(
        firebaseRemoteDatabase: FirebaseRemoteDatabase,
    ): InvoiceRepository =
        InvoiceRepositoryImpl(firebaseRemoteDatabase)

}
