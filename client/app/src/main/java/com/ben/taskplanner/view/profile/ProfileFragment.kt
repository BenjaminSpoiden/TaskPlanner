package com.ben.taskplanner.view.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ben.taskplanner.databinding.FragmentProfileBinding
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.user.User
import com.ben.taskplanner.view.BaseFragment
import com.ben.taskplanner.view.SharedTokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val sharedTokenViewModel by viewModels<SharedTokenViewModel>()

    override fun bindFragment(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
    }
    override fun bindViewModel(): Class<ProfileViewModel> = ProfileViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Tag", "TEST one")
        sharedTokenViewModel.readAccessToken().observe(viewLifecycleOwner) { accessToken ->
            accessToken?.let {
                viewModel.getCurrentUser(it)
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            Log.d("Tag", "TEST two")
            when(it) {
                is ResponseHandler.SuccessResponse -> {
                    it.response.user?.let { user ->
                        updateUI(user = user)
                    }
                }
                is ResponseHandler.FailureResponse -> {
                    Log.d("Tag", "Failure: ${it.responseBody?.string()}")
                }
                is ResponseHandler.Loading -> {
                    Log.d("Tag", "Loading...")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(user: User) {
        binding.displayName.text = "${user.name} ${user.surname}"
        binding.displayEmail.text = user.email
    }
}