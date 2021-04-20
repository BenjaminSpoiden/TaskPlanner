package com.ben.taskplanner.repository

import com.ben.taskplanner.network.TaskPlannerService
import com.ben.taskplanner.util.HeadersProvider
import javax.inject.Inject

class TaskRepository @Inject constructor(
        private val taskPlannerService: TaskPlannerService,
        private val headersProvider: HeadersProvider
    ): BaseRepository() {

    suspend fun fetchTasks(accessToken: String) = callHandler {
        taskPlannerService.fetchTasks(authHeaders = headersProvider.getAuthenticatedHeader(accessToken))
    }
}