package edu.student.groom.onboarding.login.model

import edu.student.groom.onboarding.login.model.service.LoginService
import edu.student.groom.onboarding.signup.model.service.SingUpService

class LoginRepo(private val loginService: LoginService = LoginService()) {

    suspend fun logIn(loginRequest: LoginRequest):LoginResponse{
        return loginService.loginApi.login(loginRequest)
    }
}