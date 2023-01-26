package edu.student.groom.onboarding.signup.model.service

import edu.student.groom.onboarding.signup.model.Responses
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class SingUpService {


    lateinit var signupApi: SignupApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8765/institute/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        signupApi = retrofit.create(SignupApi::class.java)
    }

    interface SignupApi {
        @GET("onboarding/institutes")
       suspend fun getInstitutes(): Responses.InstituteResponse
    }
}