package edu.student.groom.onboarding.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.student.groom.data.DefaultLoginRepo
import edu.student.groom.data.LoginRepo
import edu.student.groom.data.UserRepo
import edu.student.groom.onboarding.login.model.LoginRequest
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo,
    private val userRepo: UserRepo
) : ViewModel() {


    private val _loginResponseFlow: MutableStateFlow<UiState<LoginResponse>?> =
        MutableStateFlow(null)

    val loginResponseFlow: StateFlow<UiState<LoginResponse>?> = _loginResponseFlow

    fun login(userName: String, password: String) {
        _loginResponseFlow.value = UiState.Loading
        viewModelScope.launch {
            loginRepo.logIn(LoginRequest(userName, password))
                .catch { e ->
                    _loginResponseFlow.value = UiState.Error(e.message ?: "Api call failed")
                }.collect { response ->
                    userRepo.saveUserToken(response.access_token)
                    _loginResponseFlow.value = UiState.Success(response)
                }
        }

    }
}
