package edu.student.groom.util

import edu.student.groom.data.GroomPreferenceDataStoreAPI
import edu.student.groom.data.PreferenceDataStoreConstants.ACCESS_TOKEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


class AccessTokenManager @Inject constructor(private val groomPreferenceDataStoreAPI: GroomPreferenceDataStoreAPI) {

    private var currentToken: String = ""
    private val accessTokenJob = Job()
    private val accessTokenScope = CoroutineScope(Dispatchers.IO + accessTokenJob)
    init {
        accessTokenScope.launch(Dispatchers.IO) {
            groomPreferenceDataStoreAPI.getPreference(ACCESS_TOKEN,"")
                .collect { token -> currentToken = token }
        }

    }

    fun getAccessToken(): String {
        return currentToken
    }

    suspend fun saveAccessToken(token: String) {
        groomPreferenceDataStoreAPI.putPreference(ACCESS_TOKEN,token)
        currentToken = token  // Update the cached token
    }
}