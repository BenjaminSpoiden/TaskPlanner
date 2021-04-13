package com.ben.taskplanner.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ben.taskplanner.repository.TaskPlannerRepository
import com.ben.taskplanner.view.credentials.LoginViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val taskPlannerRepository: TaskPlannerRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(taskPlannerRepository) as T
            else -> throw Exception("View Model class does not exists")
        }
    }
}