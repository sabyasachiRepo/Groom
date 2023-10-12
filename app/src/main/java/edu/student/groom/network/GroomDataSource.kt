package edu.student.groom.network

import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.onboarding.signup.model.Responses


interface GroomDataSource {
    suspend fun getInstitutes(): Responses.InstituteResponse

    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun signUp(singUpRequest: Requests.SignUpRequest): Responses.BaseResponse
}