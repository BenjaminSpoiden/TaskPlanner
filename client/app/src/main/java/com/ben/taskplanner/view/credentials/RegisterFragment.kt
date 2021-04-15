package com.ben.taskplanner.view.credentials

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentRegisterBinding
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.AuthResponse
import com.ben.taskplanner.model.user.CreateUserModel
import com.ben.taskplanner.view.BaseFragment
import com.ben.taskplanner.view.SharedTokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    private val sharedTokenViewModel: SharedTokenViewModel by viewModels()

    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater, container, false)
    }

    override fun bindViewModel(): Class<RegisterViewModel> {
        return RegisterViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonState()
        setInputs()

        binding.signUpButton.setOnClickListener {
            viewModel.onCreateUser(CreateUserModel(
                email = binding.emailInput.editText?.text.toString(),
                password = binding.passwordInput.editText?.text.toString(),
                name = binding.nameInput.editText?.text.toString(),
                surname = binding.surnameInput.editText?.text.toString()
            ))
            registerResponse()
        }
    }

    private fun buttonState() {
        lifecycleScope.launch {
            viewModel.buttonEnabledStateFlow.collect {
                binding.signUpButton.isEnabled = it
            }
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
        binding.nameInput.editText?.addTextChangedListener {
            viewModel.setName(it.toString())
            binding.nameInput.error = null
        }
        binding.surnameInput.editText?.addTextChangedListener {
            viewModel.setSurname(it.toString())
            binding.surnameInput.error = null
        }
    }

    private fun registerResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseHandler.SuccessResponse -> {
                    sharedTokenViewModel.writeAccessToken(it.response.user!!)
                }
                is ResponseHandler.FailureResponse -> {
                    val failureResponse = onConvertResponseToModelClass<AuthResponse>(it.responseBody)
                    failureResponse?.error?.let { errorResponse ->
                        if(errorResponse.toString().contains("unique")) {
                            binding.emailInput.error = "This email already exists."
                        } else {
                            binding.passwordInput.error = errorResponse.toString()
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