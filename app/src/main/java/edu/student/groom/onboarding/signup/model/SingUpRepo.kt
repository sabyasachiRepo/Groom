package edu.student.groom.onboarding.signup.model

import android.util.Log
import edu.student.groom.onboarding.signup.model.service.SingUpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingUpRepo(private val singUpService: SingUpService = SingUpService()) {

   suspend fun getInstitutes(): Responses.InstituteResponse {
         return singUpService.signupApi.getInstitutes()
    }
}