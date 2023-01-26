package edu.student.groom.onboarding.signup.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.student.groom.onboarding.signup.model.Responses
import edu.student.groom.onboarding.signup.model.SingUpRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback

class SignupViewModel(private val singUpRepo: SingUpRepo = SingUpRepo()) : ViewModel() {

    val institutes: MutableState<List<Responses.Institute>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val institutesResponse = getInstitutes().institutes
            institutes.value = institutesResponse
        }
    }


    private suspend fun getInstitutes(): Responses.InstituteResponse {
        return singUpRepo.getInstitutes()
    }

}