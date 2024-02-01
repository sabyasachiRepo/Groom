package edu.student.groom.data

interface UserRepo {
    suspend fun saveUserToken(token:String)
}