package edu.student.groom.onboarding.signup.model

import android.util.Log
import edu.student.groom.onboarding.signup.model.service.SingUpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingUpRepo(private val singUpService: SingUpService = SingUpService()) {

   fun getInstitutes(successCallback: (response:Responses.InstituteResponse?)->Unit){
         singUpService.signupApi.getInstitutes().enqueue(object: Callback<Responses.InstituteResponse>{
             override fun onResponse(
                 call: Call<Responses.InstituteResponse>,
                 response: Response<Responses.InstituteResponse>
             ) {
                 successCallback(response.body())
             }

             override fun onFailure(call: Call<Responses.InstituteResponse>, t: Throwable) {
                Log.d("Retrofit Error",t.message.orEmpty())
             }

         })
    }
}