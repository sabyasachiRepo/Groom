package edu.student.groom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.student.groom.data.GroomPreferenceHelper
import edu.student.groom.data.PreferenceDataStoreConstants.ACCESS_TOKEN
import edu.student.groom.home.homeScreenRoute
import edu.student.groom.onboarding.login.ui.navigation.loginRoute
import edu.student.groom.onboarding.signup.model.SingUpRepo
import edu.student.groom.util.AccessTokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var accessTokenManager: AccessTokenManager


    val uiState: StateFlow<MainActivityUiState> = flow<MainActivityUiState>{
       val accessToken= accessTokenManager.getAccessToken()
        if(accessToken.isEmpty()){
            emit(MainActivityUiState.Success(loginRoute))
        }else{
            emit(MainActivityUiState.Success(homeScreenRoute))
        }

    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val route: String) : MainActivityUiState
}