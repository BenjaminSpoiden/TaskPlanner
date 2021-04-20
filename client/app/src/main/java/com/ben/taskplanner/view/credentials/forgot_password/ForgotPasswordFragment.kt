package com.ben.taskplanner.view.credentials.forgot_password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.ben.taskplanner.databinding.FragmentForgotPasswordBinding
import com.ben.taskplanner.model.ForgotPasswordRequest
import com.ben.taskplanner.model.ResetPasswordResponse
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.util.CustomDialog
import com.ben.taskplanner.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>() {

    private val customDialog: CustomDialog by lazy { CustomDialog(requireContext()) }

    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentForgotPasswordBinding {
        return FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
    }

    override fun bindViewModel(): Class<ForgotPasswordViewModel> {
        return ForgotPasswordViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendInstructionsButton.isEnabled = false

        binding.emailInput.editText?.addTextChangedListener {
            Log.d("Tag", it.toString())
            viewModel.setEmail(it.toString())
            binding.emailInput.error = null
            binding.sendInstructionsButton.isEnabled = android.util.Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()
        }

        binding.sendInstructionsButton.setOnClickListener {
            viewModel.sendResetPasswordRequest(ForgotPasswordRequest(email = binding.emailInput.editText?.text.toString().trim()))
            observePasswordResponse()
        }
    }

    private fun observePasswordResponse() {
        viewModel.resetPassword.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseHandler.SuccessResponse -> {
                    CustomDialog.dismissDialog()
                    Log.d("Tag", "Result: ${it.response.message}")
                    val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationCodeFragment(binding.emailInput.editText?.text.toString())
                    findNavController().navigate(action)
                }

                is ResponseHandler.FailureResponse -> {
                    CustomDialog.dismissDialog()
                    val convertedResponse = onConvertResponseToModelClass<ResetPasswordResponse>(responseBody = it.responseBody)
                    binding.emailInput.error = convertedResponse?.message
                }

                is ResponseHandler.Loading -> {
                    customDialog.showDialog("Sending an email...")
                    binding.root.isClickable = false
                }
            }
        }
    }
}