package com.ben.taskplanner.view.credentials.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.model.ResetPasswordModel
import com.ben.taskplanner.model.ResetPasswordResponse
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    private val _confirmPassword: MutableStateFlow<String> = MutableStateFlow("")

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    val buttonState: Flow<Boolean> = combine(_password, _confirmPassword) { password, confirmPassword  ->
        val isPasswordCorrect = password.length >= 8
        val isConfirmPasswordCorrect = password == confirmPassword && confirmPassword.length >= 8

        return@combine isPasswordCorrect and isConfirmPasswordCorrect
    }

    fun resetPassword(
            verificationToken: String,
            email: String,
            resetPasswordModel: ResetPasswordModel,
            onResponse: ((ResponseHandler<ResetPasswordResponse>) -> Unit)? = null
    ) {
        viewModelScope.launch {
            onResponse?.invoke(ResponseHandler.Loading)
            delay(2000)
            onResponse?.invoke(
                    userRepository.resetPassword(
                    verificationToken = verificationToken,
                    email = email,
                    resetPasswordModel = resetPasswordModel
            ))
        }
    }
}