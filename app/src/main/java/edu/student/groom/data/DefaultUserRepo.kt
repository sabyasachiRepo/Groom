package edu.student.groom.data

import edu.student.groom.util.AccessTokenManager
import javax.inject.Inject

class DefaultUserRepo (private val accessTokenManager: AccessTokenManager):UserRepo {
    override suspend fun saveUserToken(token: String) {
        accessTokenManager.saveAccessToken(token)
    }
}