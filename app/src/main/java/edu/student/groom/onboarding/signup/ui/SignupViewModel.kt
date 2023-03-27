package edu.student.groom.onboarding.signup.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.onboarding.signup.model.Responses
import edu.student.groom.onboarding.signup.model.SingUpRepo
import edu.student.groom.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val singUpRepo: SingUpRepo = SingUpRepo()) : ViewModel() {

    val institutes: MutableState<List<Responses.Institute>> = mutableStateOf(emptyList())
    val dropDownButtonName: MutableState<String> = mutableStateOf("Loading")
    val singUpResponse: LiveData<UiState<String>>
        get() = _singUpResponse

    private val _singUpResponse: MutableLiveData<UiState<String>> =
        MutableLiveData<UiState<String>>()


    var selectedInstitute: Responses.Institute? = null
        set(value) {
            field = value
            if (value != null) {
                dropDownButtonName.value = value.name
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

    fun signUp(signUpRequest: Requests.SignUpRequest) {
        _singUpResponse.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response: Responses.BaseResponse = singUpRepo.signUp(signUpRequest)
            if (response.status=="200") {
                withContext(Dispatchers.Main) {
                    _singUpResponse.value = UiState.Success(response.message)
                }
            } else {
                withContext(Dispatchers.Main) {
                    _singUpResponse.value = UiState.Error(response.message)
                }
            }
        }
    }

}