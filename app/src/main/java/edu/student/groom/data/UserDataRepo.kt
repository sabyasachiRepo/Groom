package edu.student.groom.data

import kotlinx.coroutines.flow.Flow

interface UserDataRepo {
    suspend fun saveUserToken(token:String)

    suspend fun getUserToken(): Flow<String>

    suspend fun removeUserToken()
}