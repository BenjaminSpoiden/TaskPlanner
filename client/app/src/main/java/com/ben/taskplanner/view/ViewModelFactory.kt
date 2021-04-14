package com.ben.taskplanner.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ben.taskplanner.repository.TaskPlannerRepository
import com.ben.taskplanner.util.TaskPlannerDataStore
import com.ben.taskplanner.view.credentials.LoginViewModel
import com.ben.taskplanner.view.my_task.MyTaskViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val taskPlannerRepository: TaskPlannerRepository,
    private val taskPlannerDataStore: TaskPlannerDataStore
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(taskPlannerRepository) as T
            modelClass.isAssignableFrom(MyTaskViewModel::class.java) -> MyTaskViewModel(taskPlannerRepository) as T
            else -> throw Exception("View Model class does not exists")
        }
    }
}