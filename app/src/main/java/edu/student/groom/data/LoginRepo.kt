package edu.student.groom.data

import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoginRepo {
    fun logIn(loginRequest: LoginRequest): Flow<Response<LoginResponse>>
}