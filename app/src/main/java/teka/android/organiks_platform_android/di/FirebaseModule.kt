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



//    @Singleton
//    @Provides
//    @Named("fruitCollection")
//    fun provideFruitCollection(): CollectionReference {
//        return FirebaseFirestore
//            .getInstance()
//            .collection(FirebaseConstants.FRUIT_COLLECTIONS)
//    }
//
//
//    @Singleton
//    @Provides
//    @Named("customerCollection")
//    fun provideCustomerCollection(): CollectionReference {
//        return FirebaseFirestore
//            .getInstance()
//            .collection(FirebaseConstants.CUSTOMER_COLLECTION)
//    }
//
//    @Singleton
//    @Provides
//    @Named("invoiceCollection")
//    fun provideInvoiceCollection(): CollectionReference {
//        return FirebaseFirestore
//            .getInstance()
//            .collection(FirebaseConstants.INVOICE_COLLECTION)
//    }


    @Singleton
    @Provides
    @Named("invoiceCollection")
    fun provideInvoiceCollection(
        @Named("userId") userId: String
    ): CollectionReference {
        return FirebaseFirestore
            .getInstance()
            .collection("tenants") // Root collection for tenants
            .document(userId)    // Tenant document
            .collection(FirebaseConstants.INVOICE_COLLECTION) // Subcollection for invoices
    }

    @Singleton
    @Provides
    @Named("customerCollection")
    fun provideCustomerCollection(
        @Named("userId") userId: String
    ): CollectionReference {
        return FirebaseFirestore
            .getInstance()
            .collection("tenants")
            .document(userId)
            .collection(FirebaseConstants.CUSTOMER_COLLECTION)
    }

    @Singleton
    @Provides
    @Named("fruitCollection")
    fun provideFruitCollection(
        @Named("userId") userId: String
    ): CollectionReference {
        return FirebaseFirestore
            .getInstance()
            .collection("tenants")
            .document(userId)
            .collection(FirebaseConstants.FRUIT_COLLECTIONS)
    }


    @Singleton
    @Provides
    fun provideRemoteDatabase(
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
        roomRepository: RoomRepository,
    ): FruitCollectionRepository =
        FruitCollectionsRepositoryImpl(
            firebaseRemoteDatabase = firebaseRemoteDatabase,
            roomRepository = roomRepository
        )


    @Singleton
    @Provides
    fun provideCustomerRepository(
        firebaseRemoteDatabase: FirebaseRemoteDatabase,
        roomRepository: RoomRepository
    ): CustomerRepository =
        CustomerRepositoryImpl(
            firebaseRemoteDatabase = firebaseRemoteDatabase,
            roomRepository = roomRepository
        )


    @Singleton
    @Provides
    fun provideInvoiceRepository(
        firebaseRemoteDatabase: FirebaseRemoteDatabase,
        roomRepository: RoomRepository
    ): InvoiceRepository =
        InvoiceRepositoryImpl(
            firebaseRemoteDatabase = firebaseRemoteDatabase,
            roomRepository = roomRepository
        )

}
