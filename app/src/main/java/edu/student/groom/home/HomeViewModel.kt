package edu.student.groom.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.student.groom.data.UserDataRepo
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel  @Inject constructor(private val userDataRepo: UserDataRepo):ViewModel() {

    fun logout(){
        viewModelScope.launch {
            userDataRepo.removeUserToken()
        }
    }
}