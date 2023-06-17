package teka.android.organiks_platform_android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import teka.android.organiks_platform_android.modules.splash_screen.DataStoreRepository
import teka.android.organiks_platform_android.modules.splash_screen.presentation.SplashViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

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

}