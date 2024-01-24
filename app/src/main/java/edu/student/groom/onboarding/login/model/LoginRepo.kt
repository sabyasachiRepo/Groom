package edu.student.groom.onboarding.login.model

import edu.student.groom.onboarding.login.model.service.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepo @Inject constructor(private val loginService: LoginService) {



     fun logIn(loginRequest: LoginRequest): Flow<LoginResponse> = flow {
         emit(loginService.loginApi.login(loginRequest))
     }.flowOn(Dispatchers.IO).catch {

     }
}