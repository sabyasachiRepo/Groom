package edu.student.groom.util

import androidx.datastore.preferences.core.stringPreferencesKey
import edu.student.groom.data.GroomLocalDataSource
import edu.student.groom.util.PreferenceDataStoreConstants.ACCESS_TOKEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class AccessTokenManager(private val groomLocalDataSource: GroomLocalDataSource) {


    suspend fun getAccessToken(): Flow<String> {
        return  groomLocalDataSource.getPreference(ACCESS_TOKEN,"")
    }

    suspend fun saveAccessToken(token: String) {
        groomLocalDataSource.putPreference(ACCESS_TOKEN,token)
    }

    suspend fun clearAccessToken() {
        groomLocalDataSource.removePreference(ACCESS_TOKEN)
    }
}


object PreferenceDataStoreConstants {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")

}