package edu.student.groom.onboarding.login.model.service

import edu.student.groom.Network
import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.onboarding.signup.model.Responses
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class LoginService {


    var loginApi: LoginApi

    init {
        loginApi = Network.getInstance().retrofit.create(LoginApi::class.java)
    }

    interface LoginApi {

        @POST("onboarding/login")
        suspend fun login(@Body loginRequest: LoginRequest):LoginResponse
    }


}