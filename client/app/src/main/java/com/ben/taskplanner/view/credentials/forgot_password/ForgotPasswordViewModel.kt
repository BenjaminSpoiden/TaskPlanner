package com.ben.taskplanner.view.credentials.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.model.ForgotPasswordRequest
import com.ben.taskplanner.model.ResetPasswordResponse
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _email: MutableLiveData<String> = MutableLiveData()

    private var _resetPassword: MutableLiveData<ResponseHandler<ResetPasswordResponse>> = MutableLiveData()
    val resetPassword: LiveData<ResponseHandler<ResetPasswordResponse>> get() = _resetPassword

    fun setEmail(email: String) {
        _email.value = email
    }

    fun sendResetPasswordRequest(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            _resetPassword.value = ResponseHandler.Loading
            delay(2000)
            _resetPassword.value = userRepository.sendPasswordResetRequest(forgotPasswordRequest)
        }
    }
}