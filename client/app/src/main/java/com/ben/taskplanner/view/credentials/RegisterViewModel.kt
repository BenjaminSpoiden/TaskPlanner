package com.ben.taskplanner.view.credentials

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.AuthResponse
import com.ben.taskplanner.model.user.CreateUserModel
import com.ben.taskplanner.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    private val _surname: MutableStateFlow<String> = MutableStateFlow("")
    private val _password: MutableStateFlow<String> = MutableStateFlow("")


    fun setEmail(email: String) {
        _email.value = email
    }
    fun setName(name: String) {
        _name.value = name
    }
    fun setSurname(surname: String) {
        _surname.value = surname
    }
    fun setPassword(password: String) {
        _password.value = password
    }

    val buttonEnabledStateFlow = combine(_email, _password, _name, _surname) { email, password, name, surname ->
        val isEmailCorrect = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
        val isPasswordCorrect = password.length >= 8
        val isNameCorrect = name.length >= 2
        val isSurnameCorrect = surname.length >= 2

        return@combine isEmailCorrect and isPasswordCorrect and isNameCorrect and isSurnameCorrect
    }

    private val _response: MutableLiveData<ResponseHandler<AuthResponse>> = MutableLiveData()
    val response: LiveData<ResponseHandler<AuthResponse>> get() = _response

    fun onCreateUser(userRequest: CreateUserModel) {
        viewModelScope.launch {
            _response.value = ResponseHandler.Loading
            _response.value = authRepository.onCreateUser(userRequest)
        }
    }
}