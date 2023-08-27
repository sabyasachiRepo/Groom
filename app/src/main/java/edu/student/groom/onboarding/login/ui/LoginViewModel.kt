package edu.student.groom.onboarding.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.student.groom.onboarding.login.model.LoginRepo
import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val loginRepo: LoginRepo = LoginRepo()) : ViewModel() {

    val loginResponse: LiveData<UiState<LoginResponse>>
        get() = _loginResponse

    private val _loginResponse = MutableLiveData<UiState<LoginResponse>>()

    private val _loginResponseFlow: MutableStateFlow<UiState<LoginResponse>?> = MutableStateFlow(null)

    val loginResponseFlow: StateFlow<UiState<LoginResponse>?> = _loginResponseFlow

    /* fun login(userName: String, password: String) {
         _loginResponse.value = UiState.Loading

         viewModelScope.launch {
             try {
                 val response = loginRepo.logIn(LoginRequest(userName, password))
                 _loginResponse.value = UiState.Success(response)
             } catch (e: Exception) {
                 _loginResponse.value = UiState.Error("Login Failed")
             }
         }
     }*/
    fun login(userName: String, password: String) {
        _loginResponseFlow.value=UiState.Loading
        viewModelScope.launch {
            loginRepo.logIn(LoginRequest(userName, password))
                .catch { e ->
                    _loginResponseFlow.value = UiState.Error(e.message ?: "Api call failed")
                }.collect { response ->
                    _loginResponseFlow.value = UiState.Success(response)
                }
        }


    }
}
