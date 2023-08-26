package edu.student.groom.onboarding.login.model

import edu.student.groom.onboarding.login.model.service.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepo(private val loginService: LoginService = LoginService()) {

   /* suspend fun logIn(loginRequest: LoginRequest):LoginResponse{
        return loginService.loginApi.login(loginRequest)
    }*/

    fun logIn(loginRequest: LoginRequest): Flow<LoginResponse> = flow {
        emit(loginService.loginApi.login(loginRequest))
    }
}