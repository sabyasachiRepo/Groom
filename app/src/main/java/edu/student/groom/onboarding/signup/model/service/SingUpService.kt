package edu.student.groom.onboarding.signup.model.service

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

class SingUpService {


    lateinit var signupApi: SignupApi

    init {
         val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

         val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8765/institute/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        signupApi = retrofit.create(SignupApi::class.java)
    }

    interface SignupApi {
        @GET("onboarding/institutes")
        suspend fun getInstitutes(): Responses.InstituteResponse

        @POST("onboarding/registration")
        suspend fun signUp(@Body singUpRequest: Requests.SignUpRequest):Responses.BaseResponse
    }


}