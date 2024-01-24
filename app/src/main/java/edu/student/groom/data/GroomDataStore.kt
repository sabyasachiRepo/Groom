package edu.student.groom.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow


object PreferenceDataStoreConstants {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")

}


