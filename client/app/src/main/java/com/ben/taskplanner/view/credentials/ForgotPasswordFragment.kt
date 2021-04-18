package com.ben.taskplanner.view.credentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentForgotPasswordBinding
import com.ben.taskplanner.view.BaseFragment

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding, LoginViewModel>() {

    override fun bindFragment(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentForgotPasswordBinding {
        return FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
    }

    override fun bindViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendInstructionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_verificationCodeFragment)
        }
    }
}