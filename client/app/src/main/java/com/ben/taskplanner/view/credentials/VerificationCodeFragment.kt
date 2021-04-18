package com.ben.taskplanner.view.credentials

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import androidx.lifecycle.ViewModelProvider
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentVerificationCodeBinding
import com.ben.taskplanner.view.BaseFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class VerificationCodeFragment : Fragment(){

    lateinit var binding: FragmentVerificationCodeBinding
    private lateinit var verificationCodeViewModel: VerificationCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodeBinding.inflate(inflater)
        verificationCodeViewModel = ViewModelProvider(this).get(VerificationCodeViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.verificationCodeViewModel = verificationCodeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}