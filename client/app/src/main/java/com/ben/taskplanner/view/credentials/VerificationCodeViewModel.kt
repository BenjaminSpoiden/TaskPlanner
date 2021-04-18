package com.ben.taskplanner.view.credentials

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ben.taskplanner.interfaces.NumPadListener

class VerificationCodeViewModel: ViewModel() {

    val pinCode = MutableLiveData<String>()
    val buttonState = MutableLiveData(false)


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
            Log.d("Tag", "Contains 6 digit: ${pinCode.value}")
        }
    }
}