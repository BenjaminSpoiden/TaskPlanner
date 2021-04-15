package com.ben.taskplanner.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.CurrentUserResponse
import com.ben.taskplanner.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _user: MutableLiveData<ResponseHandler<CurrentUserResponse>> = MutableLiveData()
    val user: LiveData<ResponseHandler<CurrentUserResponse>> get() = _user

    fun getCurrentUser(accessToken: String) {
        viewModelScope.launch {
            _user.value = ResponseHandler.Loading
            _user.value = userRepository.getCurrentUser(accessToken)
        }
    }
}