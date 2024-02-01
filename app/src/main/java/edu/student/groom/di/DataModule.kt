package edu.student.groom.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.student.groom.data.DefaultLoginRepo
import edu.student.groom.data.DefaultUserRepo
import edu.student.groom.data.GroomPreferenceDataStoreAPI
import edu.student.groom.data.GroomPreferenceHelper
import edu.student.groom.data.LoginRepo
import edu.student.groom.data.UserRepo
import edu.student.groom.onboarding.login.model.service.LoginService
import edu.student.groom.util.AccessTokenManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideGroomDataStore(
        @ApplicationContext context: Context
        // Potential dependencies of this type
    ): GroomPreferenceDataStoreAPI {
        return GroomPreferenceHelper(context)
    }

    @Singleton
    @Provides
    fun provideLoginRepo(loginService: LoginService
    ): LoginRepo {
        return DefaultLoginRepo(loginService)
    }

    @Singleton
    @Provides
    fun provideUserRepo(accessTokenManager: AccessTokenManager
    ): UserRepo {
        return DefaultUserRepo(accessTokenManager)
    }
}