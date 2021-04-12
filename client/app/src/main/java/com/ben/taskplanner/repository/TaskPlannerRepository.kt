package com.ben.taskplanner.repository

import com.ben.taskplanner.model.user.CreateUserModel
import com.ben.taskplanner.model.user.LoginUserModel
import com.ben.taskplanner.network.TaskPlannerService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class TaskPlannerRepository @Inject constructor(private val taskPlannerService: TaskPlannerService): BaseRepository() {

    suspend fun onCreateUser(userRequest: CreateUserModel) = callHandler {
        taskPlannerService.onCreateUser(userRequest)
    }
    suspend fun onLoginUser(userRequest: LoginUserModel) = callHandler {
        taskPlannerService.onLoginUser(userRequest)
    }
}