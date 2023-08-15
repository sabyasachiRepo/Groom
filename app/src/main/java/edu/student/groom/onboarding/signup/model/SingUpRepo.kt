package edu.student.groom.onboarding.signup.model

import edu.student.groom.onboarding.signup.model.service.SingUpService

class SingUpRepo(private val singUpService: SingUpService = SingUpService()) {

   suspend fun getInstitutes(): Responses.InstituteResponse {
         return singUpService.signupApi.getInstitutes()
    }

    suspend fun signUp(signUpRequest: Requests.SignUpRequest): Responses.BaseResponse {
        return singUpService.signupApi.signUp(signUpRequest)
    }
}