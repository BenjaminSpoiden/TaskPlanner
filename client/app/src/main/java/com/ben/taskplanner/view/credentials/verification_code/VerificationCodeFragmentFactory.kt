package com.ben.taskplanner.view.credentials.verification_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ben.taskplanner.repository.UserRepository
import javax.inject.Inject

class VerificationCodeFragmentFactory @Inject constructor(val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(VerificationCodeViewModel::class.java) -> VerificationCodeViewModel(userRepository) as T
            else -> throw Exception("View Model doesn't exist.")
        }
    }
}