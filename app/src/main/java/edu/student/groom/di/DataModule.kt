package edu.student.groom.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.student.groom.data.DefaultLoginRepo
import edu.student.groom.data.DefaultUserRepo
import edu.student.groom.data.GroomLocalDataSource
import edu.student.groom.data.GroomPreferenceDataSource
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
    ): GroomLocalDataSource {
        return GroomPreferenceDataSource(context)
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