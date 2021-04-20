package com.ben.taskplanner.repository

import com.ben.taskplanner.model.ForgotPasswordRequest
import com.ben.taskplanner.model.ResetPasswordModel
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

    suspend fun sendPasswordResetRequest(forgotPasswordRequest: ForgotPasswordRequest) = callHandler {
        taskPlannerService.sendResetPasswordRequest(forgotPasswordRequest)
    }

    suspend fun authorizeVerificationToken(token: String, email: String) = callHandler {
        taskPlannerService.authorizeVerificationToken(token = token, email = email)
    }

    suspend fun resetPassword(verificationToken: String?, email: String, resetPasswordModel: ResetPasswordModel) = callHandler {
        taskPlannerService.resetPassword(
            authHeaders = headersProvider.getVerificationToken(verificationToken),
            email = email,
            resetPasswordModel = resetPasswordModel
        )
    }
}