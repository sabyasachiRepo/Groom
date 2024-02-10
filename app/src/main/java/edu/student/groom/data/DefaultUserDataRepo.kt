package edu.student.groom.data

import edu.student.groom.util.AccessTokenManager
import kotlinx.coroutines.flow.Flow

class DefaultUserDataRepo (private val accessTokenManager: AccessTokenManager):UserDataRepo {
    override suspend fun saveUserToken(token: String) {
        accessTokenManager.saveAccessToken(token)
    }

    override suspend fun getUserToken(): Flow<String> {
      return  accessTokenManager.getAccessToken()
    }

    override suspend fun removeUserToken() {
        accessTokenManager.clearAccessToken()
    }


}