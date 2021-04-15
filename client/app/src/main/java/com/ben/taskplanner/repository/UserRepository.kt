package com.ben.taskplanner.repository

import com.ben.taskplanner.network.TaskPlannerService
import com.ben.taskplanner.util.HeadersProvider
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val taskPlannerService: TaskPlannerService,
    private val headersProvider: HeadersProvider
    ): BaseRepository() {

    suspend fun getCurrentUser(accessToken: String?) = callHandler {
        taskPlannerService.getCurrentUser(authHeaders = headersProvider.getAuthenticatedHeader(accessToken))
    }
}