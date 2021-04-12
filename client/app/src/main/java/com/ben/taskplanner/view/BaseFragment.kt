package com.ben.taskplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ben.taskplanner.repository.TaskPlannerRepository
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>: Fragment() {

    @Inject lateinit var taskPlannerRepository: TaskPlannerRepository
    @Inject lateinit var gson: Gson

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected val viewModel: VM by lazy {
        val viewModelFactory = ViewModelFactory(taskPlannerRepository)
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