package com.ben.taskplanner

import com.ben.taskplanner.network.TaskPlannerService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TaskPlannerRepository @Inject constructor(taskPlannerService: TaskPlannerService) {
}