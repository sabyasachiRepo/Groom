package edu.student.groom.onboarding.signup.model.service

import edu.student.groom.Network
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.onboarding.signup.model.Responses
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class SingUpService {


    lateinit var signupApi: SignupApi

    init {
        signupApi = Network.getInstance().retrofit.create(SignupApi::class.java)
    }

    interface SignupApi {
        @GET("onboarding/institutes")
        suspend fun getInstitutes(): Responses.InstituteResponse

        @POST("onboarding/registration")
        suspend fun signUp(@Body singUpRequest: Requests.SignUpRequest):Responses.BaseResponse
    }


}