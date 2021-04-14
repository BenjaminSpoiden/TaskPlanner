package com.ben.taskplanner.view.credentials

import androidx.lifecycle.*
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.LoginUserModel
import com.ben.taskplanner.model.user.UserResponse
import com.ben.taskplanner.repository.TaskPlannerRepository
import com.ben.taskplanner.util.TaskPlannerDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val taskPlannerRepository: TaskPlannerRepository
) : ViewModel() {

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    private val _password: MutableStateFlow<String> = MutableStateFlow("")

    fun setEmail(email: String) {
        _email.value = email
    }
    fun setPassword(password: String) {
        _password.value = password
    }

    val buttonEnabledStateFlow = combine(_email, _password) { email, password ->
        val isEmailCorrect = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
        val isPasswordCorrect = password.length >= 8

        return@combine isEmailCorrect and isPasswordCorrect
    }


    private val _response: MutableLiveData<ResponseHandler<UserResponse>> = MutableLiveData()
    val response: LiveData<ResponseHandler<UserResponse>> get() = _response

    fun onLoginUser(userRequest: LoginUserModel) {
        viewModelScope.launch {
            _response.value = ResponseHandler.Loading
            _response.value = taskPlannerRepository.onLoginUser(userRequest)
        }
    }
}