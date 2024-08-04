package teka.android.organiks_platform_android.presentation.feature_firebase_auth

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseAuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository = impl
}