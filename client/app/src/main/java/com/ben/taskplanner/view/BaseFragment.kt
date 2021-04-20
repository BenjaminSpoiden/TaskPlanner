package com.ben.taskplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ben.taskplanner.repository.AuthRepository
import com.ben.taskplanner.repository.TaskRepository
import com.ben.taskplanner.repository.UserRepository
import com.ben.taskplanner.util.TaskPlannerDataStore
import com.google.gson.Gson
import okhttp3.ResponseBody
import javax.inject.Inject

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>: Fragment() {

    @Inject lateinit var authRepository: AuthRepository
    @Inject lateinit var gson: Gson
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var taskRepository: TaskRepository
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected val viewModel: VM by lazy {
        val viewModelFactory = ViewModelFactory(authRepository, userRepository, taskRepository)
        ViewModelProvider(this, viewModelFactory).get(bindViewModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindFragment(inflater, container, savedInstanceState)
        return binding.root
    }

    abstract fun bindFragment(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB
    abstract fun bindViewModel(): Class<VM>

    protected inline fun <reified C> onConvertResponseToModelClass(responseBody: ResponseBody?): C? {
        return gson.fromJson(responseBody?.string(), C::class.java)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}