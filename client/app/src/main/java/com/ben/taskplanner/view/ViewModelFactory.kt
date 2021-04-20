package com.ben.taskplanner.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ben.taskplanner.repository.AuthRepository
import com.ben.taskplanner.repository.TaskRepository
import com.ben.taskplanner.repository.UserRepository
import com.ben.taskplanner.view.credentials.forgot_password.ForgotPasswordViewModel
import com.ben.taskplanner.view.credentials.login.LoginViewModel
import com.ben.taskplanner.view.credentials.register.RegisterViewModel
import com.ben.taskplanner.view.credentials.reset_password.ResetPasswordViewModel
import com.ben.taskplanner.view.credentials.verification_code.VerificationCodeViewModel
import com.ben.taskplanner.view.my_task.MyTaskViewModel
import com.ben.taskplanner.view.profile.ProfileViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(authRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(authRepository) as T
            modelClass.isAssignableFrom(MyTaskViewModel::class.java) -> MyTaskViewModel(taskRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(userRepository) as T
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel(userRepository) as T
            modelClass.isAssignableFrom(ResetPasswordViewModel::class.java) -> ResetPasswordViewModel(userRepository) as T
            else -> throw Exception("View Model class does not exists")
        }
    }
}