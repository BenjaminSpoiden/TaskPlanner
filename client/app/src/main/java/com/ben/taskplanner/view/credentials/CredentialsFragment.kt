package com.ben.taskplanner.view.credentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentCredentialsBinding
import com.ben.taskplanner.view.BaseFragment
import com.google.android.material.button.MaterialButton

class CredentialsFragment : Fragment() {

    private var _binding: FragmentCredentialsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCredentialsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_credentialsFragment_to_loginFragment)
        }
        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_credentialsFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}