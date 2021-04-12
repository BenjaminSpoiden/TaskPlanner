package com.ben.taskplanner.view.my_task

import androidx.lifecycle.ViewModel
import com.ben.taskplanner.repository.TaskPlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyTaskViewModel @Inject constructor(val taskPlannerRepositoryFactory: TaskPlannerRepository): ViewModel() {
}