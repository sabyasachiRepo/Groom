package edu.student.groom.onboarding.signup.ui

import androidx.lifecycle.ViewModel
import edu.student.groom.onboarding.signup.model.Responses
import edu.student.groom.onboarding.signup.model.SingUpRepo
import retrofit2.Callback

class SignupViewModel(private val singUpRepo: SingUpRepo= SingUpRepo()):ViewModel() {

    fun getInstitutes(successCallback: (successResponse:Responses.InstituteResponse?)->Unit) {
        singUpRepo.getInstitutes(successCallback)
    }

}