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
    val dropDownButtonName: MutableState<String> = mutableStateOf("Loading")
    var selectedInstitute: Responses.Institute?=null
                      set(value){
                          field=value
                          if (value != null) {
                              dropDownButtonName.value=value.name
                          }
                      }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val institutesResponse = getInstitutes().institutes
            institutes.value = institutesResponse
            if (institutesResponse.isNotEmpty()) {
                dropDownButtonName.value = "Select Institute"
            }
        }
    }



    private suspend fun getInstitutes(): Responses.InstituteResponse {
        return singUpRepo.getInstitutes()
    }

}