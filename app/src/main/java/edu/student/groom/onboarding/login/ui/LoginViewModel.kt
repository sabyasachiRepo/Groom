package edu.student.groom.onboarding.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import edu.student.groom.data.GroomPreferenceHelper
import edu.student.groom.data.PreferenceDataStoreConstants.ACCESS_TOKEN
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepo: LoginRepo) : ViewModel() {

    @Inject
    lateinit var preferenceDataStoreHelper: GroomPreferenceHelper

    private val _loginResponseFlow: MutableStateFlow<UiState<LoginResponse>?> = MutableStateFlow(null)

    val loginResponseFlow: StateFlow<UiState<LoginResponse>?> = _loginResponseFlow

    fun login(userName: String, password: String) {
        _loginResponseFlow.value=UiState.Loading
        viewModelScope.launch {
            loginRepo.logIn(LoginRequest(userName, password))
                .onStart {

                }
                .catch { e ->
                    _loginResponseFlow.value = UiState.Error(e.message ?: "Api call failed")
                }.collect { response ->
                    preferenceDataStoreHelper.putPreference(ACCESS_TOKEN, response.access_token)
                    _loginResponseFlow.value = UiState.Success(response)
                }
        }

    }
}
