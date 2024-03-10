package edu.student.groom.onboarding.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.student.groom.TAG
import edu.student.groom.data.APIErrorResponse
import edu.student.groom.data.LoginRepo
import edu.student.groom.data.UserDataRepo
import edu.student.groom.getErrorMessage
import edu.student.groom.isNetworkError
import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo,
    private val userDataRepo: UserDataRepo
) : ViewModel() {

    private val _loginResponseFlow: MutableStateFlow<UiState<LoginResponse>?> =
        MutableStateFlow(null)

    val loginResponseFlow: StateFlow<UiState<LoginResponse>?> = _loginResponseFlow

    fun login(userName: String, password: String) {
        _loginResponseFlow.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = loginRepo.logIn(LoginRequest(userName, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        userDataRepo.saveUserToken(it.access_token)
                        _loginResponseFlow.value = UiState.Success(it)
                    }
                } else {
                    _loginResponseFlow.value =
                        UiState.Error(response.getErrorMessage() ?: "Api call failed")
                }
            } catch (e: Exception) {
                if (e.isNetworkError())
                    _loginResponseFlow.value =
                        UiState.Error("Please check if the device have network connectivity and try again") else {
                    UiState.Error("API error").also { _loginResponseFlow.value = it }
                }
            }
        }
    }

}

