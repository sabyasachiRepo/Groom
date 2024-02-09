package edu.student.groom.util

import edu.student.groom.data.GroomLocalDataSource
import edu.student.groom.data.PreferenceDataStoreConstants.ACCESS_TOKEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccessTokenManager @Inject constructor(private val groomLocalDataSource: GroomLocalDataSource) {

    private var currentToken: String = ""
    private val accessTokenJob = Job()
    private val accessTokenScope = CoroutineScope(Dispatchers.IO + accessTokenJob)
    init {
        accessTokenScope.launch(Dispatchers.IO) {
            groomLocalDataSource.getPreference(ACCESS_TOKEN,"")
                .collect { token -> currentToken = token }
        }

    }

    fun getAccessToken(): String {
        return currentToken
    }

    suspend fun saveAccessToken(token: String) {
        groomLocalDataSource.putPreference(ACCESS_TOKEN,token)
        currentToken = token  // Update the cached token
    }
}