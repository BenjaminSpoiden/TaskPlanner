package com.ben.taskplanner.view.credentials.reset_password

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentResetPasswordBinding
import com.ben.taskplanner.model.ResetPasswordModel
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.util.CustomDialog
import com.ben.taskplanner.util.ExtensionFunctions.makeSnackBar
import com.ben.taskplanner.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding, ResetPasswordViewModel>() {

    private val args by navArgs<ResetPasswordFragmentArgs>()
    private val customDialog by lazy {
        CustomDialog(requireContext())
    }
    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentResetPasswordBinding {
        return FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
    }

    override fun bindViewModel(): Class<ResetPasswordViewModel> {
        return ResetPasswordViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputs()
        buttonState()
        binding.changePasswordButton.setOnClickListener {
            viewModel.resetPassword(
                verificationToken = args.resetPasswordParcelModel.token,
                email = args.resetPasswordParcelModel.email,
                resetPasswordModel = ResetPasswordModel(
                    password = binding.passwordInput.editText?.text.toString(),
                    confirmPassword = binding.confirmPasswordInput.editText?.text.toString()
                )
            ) {
                when (it) {
                    is ResponseHandler.Loading -> {
                        customDialog.showDialog("Updating your password.")
                    }
                    is ResponseHandler.SuccessResponse -> {
                        CustomDialog.dismissDialog()
                        view.makeSnackBar(it.response.message, "Login") {
                            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
                        }
                    }
                    is ResponseHandler.FailureResponse -> {
                        CustomDialog.dismissDialog()
                        Log.d("Tag", "${it.responseBody?.string()}")
                    }
                }
            }
        }
    }

    private fun inputs() {
        binding.passwordInput.editText?.addTextChangedListener {
            viewModel.setPassword(it.toString())
            binding.passwordInput.error = null
        }

        binding.confirmPasswordInput.editText?.addTextChangedListener {
            viewModel.setConfirmPassword(it.toString())
            binding.confirmPasswordInput.error = null
        }
    }

    private fun buttonState() {
        lifecycleScope.launch {
            viewModel.buttonState.collect {
                binding.changePasswordButton.isEnabled = it
            }
        }
    }
}