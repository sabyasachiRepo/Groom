package edu.student.groom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.student.groom.data.UserDataRepo
import edu.student.groom.home.homeScreenRoute
import edu.student.groom.onboarding.login.ui.navigation.loginRoute
import edu.student.groom.util.AccessTokenManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(userDataRepo: UserDataRepo) : ViewModel() {

    init {
        Timber.tag(MainViewModel::class.java.name)
    }

    val uiState: StateFlow<MainActivityUiState> = flow<MainActivityUiState>{
        userDataRepo.getUserToken().collect{
           if(it.isEmpty()){
               Timber.d("user access token is empty")
               emit(MainActivityUiState.Success(loginRoute))
           }else{
               emit(MainActivityUiState.Success(homeScreenRoute))
           }
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