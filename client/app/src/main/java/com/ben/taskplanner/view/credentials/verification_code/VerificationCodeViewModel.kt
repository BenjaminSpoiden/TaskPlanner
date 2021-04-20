package com.ben.taskplanner.view.credentials.verification_code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.interfaces.NumPadListener
import com.ben.taskplanner.interfaces.VerificationTokenListener
import com.ben.taskplanner.model.ResetPasswordResponse
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModel() {

    val pinCode = MutableLiveData<String>()
    val buttonState = MutableLiveData(false)

    var verificationTokenListener: VerificationTokenListener? = null

    val numPadListener = object : NumPadListener {
        override fun onNumberClicked(number: Char) {
            val existingPinCode = pinCode.value ?: ""
            val newPinCode = existingPinCode + number

            (newPinCode.length <= 6).let {
                if(it) {
                    pinCode.postValue(newPinCode)
                    buttonState.value = newPinCode.length == 6
                }
            }
        }

        override fun onErased() {
            val dropLast = pinCode.value?.dropLast(1) ?: ""
            pinCode.postValue(dropLast)
            buttonState.postValue(false)
        }

        override fun onValidate() {
            pinCode.value?.let {
                verificationTokenListener?.onValidate(it)
            }
        }
    }

    fun authorizeVerificationToken(
            email: String,
            token: String,
            onResponse: ((ResponseHandler<ResetPasswordResponse>) -> Unit)? = null) {

        viewModelScope.launch {
            onResponse?.invoke(ResponseHandler.Loading)
            delay(2000)
            onResponse?.invoke(userRepository.authorizeVerificationToken(token = token, email = email))
        }
    }

    override fun onCleared() {
        verificationTokenListener = null
        super.onCleared()
    }
}