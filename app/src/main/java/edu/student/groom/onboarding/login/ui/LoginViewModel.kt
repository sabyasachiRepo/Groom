package edu.student.groom.onboarding.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.student.groom.onboarding.login.model.LoginRepo
import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.util.UiState
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo = LoginRepo()) : ViewModel() {

    val loginResponse: LiveData<UiState<LoginResponse>>
        get() = _loginResponse

    private val _loginResponse = MutableLiveData<UiState<LoginResponse>>()

    fun login(userName: String, password: String) {
        _loginResponse.value = UiState.Loading

        viewModelScope.launch {
            try {
                val response = loginRepo.logIn(LoginRequest(userName, password))
                _loginResponse.value = UiState.Success(response)
            } catch (e: Exception) {
                _loginResponse.value = UiState.Error("Login Failed")
            }
        }
    }


    init {

    }

}