package edu.student.groom.network

import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.onboarding.signup.model.Responses
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitGroomNetwork  @Inject constructor():GroomDataSource  {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8765/institute/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    override suspend fun getInstitutes(): Responses.InstituteResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(singUpRequest: Requests.SignUpRequest): Responses.BaseResponse {
        TODO("Not yet implemented")
    }

}