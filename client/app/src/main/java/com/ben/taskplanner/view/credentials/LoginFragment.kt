package com.ben.taskplanner.view.credentials

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.ben.taskplanner.databinding.FragmentLoginBinding
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.LoginUserModel
import com.ben.taskplanner.model.user.UserResponse
import com.ben.taskplanner.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
            binding.emailInput.error = null
        }
        binding.passwordInput.editText?.addTextChangedListener {
            viewModel.setPassword(it.toString())
            binding.passwordInput.error = null
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
                    Log.d("Tag", "Success: ${it.response.user}")
                }
                is ResponseHandler.FailureResponse -> {
                    val failureResponse = onConvertResponseToModelClass<UserResponse>(it.responseBody)
                    failureResponse?.error?.let { errorResponse ->
                        if(errorResponse.toString().contains("passwords")) {
                            binding.passwordInput.error = errorResponse.toString()
                        } else {
                            binding.emailInput.error = errorResponse.toString()
                        }
                    }
                }
                is ResponseHandler.Loading -> {
                    Log.d("Tag", "Loading...")
                }
            }
        }
    }
}