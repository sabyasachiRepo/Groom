package edu.student.groom.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.student.groom.data.GroomPreferenceDataStoreAPI
import edu.student.groom.data.GroomPreferenceHelper

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideGroomDataStore(
        @ApplicationContext context: Context
        // Potential dependencies of this type
    ): GroomPreferenceDataStoreAPI {
        return GroomPreferenceHelper(context)
    }
}