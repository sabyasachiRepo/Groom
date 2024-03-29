package edu.student.groom.data

import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.onboarding.login.model.service.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


class DefaultLoginRepo (private val loginService: LoginService): LoginRepo {

    override suspend fun logIn(loginRequest: LoginRequest):Response<LoginResponse>{
       return loginService.loginApi.login(loginRequest)
    }
}