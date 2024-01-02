package edu.student.groom.onboarding.signup.model

import edu.student.groom.onboarding.signup.model.service.SingUpService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingUpRepo @Inject constructor(private val singUpService: SingUpService) {

   suspend fun getInstitutes(): Responses.InstituteResponse {
         return singUpService.signupApi.getInstitutes()
    }

    suspend fun signUp(signUpRequest: Requests.SignUpRequest): Responses.BaseResponse {
        return singUpService.signupApi.signUp(signUpRequest)
    }
}