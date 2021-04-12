package com.ben.taskplanner.view.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentLoginBinding
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.LoginUserModel
import com.ben.taskplanner.model.user.User
import com.ben.taskplanner.model.user.UserResponse
import com.ben.taskplanner.repository.TaskPlannerRepository
import com.ben.taskplanner.view.BaseFragment
import com.ben.taskplanner.view.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun bindFragment(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater, container, false)
    }

    override fun bindViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInputs()
        checkButtonState()

        binding.signInButton.setOnClickListener {
            viewModel.onLoginUser(LoginUserModel(
                binding.emailInput.editText?.text.toString(),
                binding.passwordInput.editText?.text.toString(),
            ))
            loginResponse()
        }
    }

    private fun setInputs() {
        binding.emailInput.editText?.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.passwordInput.editText?.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
    }

    private fun checkButtonState() {
        lifecycleScope.launch {
            viewModel.buttonEnabledStateFlow.collect {
                binding.signInButton.isEnabled = it
            }
        }
    }

    private fun loginResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseHandler.SuccessResponse -> {
                    Log.d("Tag", "Success: ${it.response}")
                }
                is ResponseHandler.FailureResponse -> {
                    val failureResponse = onConvertResponseToModelClass<UserResponse>(it.responseBody)
                    Log.d("Tag", "Failure: $failureResponse")
                }
                is ResponseHandler.Loading -> {
                    Log.d("Tag", "Loading...")
                }
            }
        }
    }
}