package com.ben.taskplanner.network

import com.ben.taskplanner.model.ForgotPasswordRequest
import com.ben.taskplanner.model.ResetPasswordModel
import com.ben.taskplanner.model.ResetPasswordResponse
import com.ben.taskplanner.model.task.TaskResponse
import com.ben.taskplanner.model.user.CreateUserModel
import com.ben.taskplanner.model.user.LoginUserModel
import com.ben.taskplanner.model.user.AuthResponse
import com.ben.taskplanner.model.user.CurrentUserResponse
import com.ben.taskplanner.util.AuthenticatedHeaders
import retrofit2.http.*

interface TaskPlannerService {

    // *** -- User Requests -- ***
    @POST("/register")
    suspend fun onCreateUser(@Body userRequest: CreateUserModel): AuthResponse

    @POST("/login")
    suspend fun onLoginUser(@Body userRequest: LoginUserModel): AuthResponse

    @GET("/currentUser")
    suspend fun getCurrentUser(@HeaderMap authHeaders: AuthenticatedHeaders): CurrentUserResponse


    // *** -- Password Reset -- ***
    @POST("/forgot-password")
    suspend fun sendResetPasswordRequest(@Body forgotPasswordRequest: ForgotPasswordRequest): ResetPasswordResponse

    @GET("/verification-token")
    suspend fun authorizeVerificationToken(
        @Query("token") token: String,
        @Query("email") email: String,
    ): ResetPasswordResponse

    @POST("/reset-password")
    suspend fun resetPassword(
        @HeaderMap authHeaders: AuthenticatedHeaders,
        @Query("email") email: String,
        @Body resetPasswordModel: ResetPasswordModel
    ): ResetPasswordResponse

    // *** -- Tasks -- ***
    @GET("/tasks")
    suspend fun fetchTasks(@HeaderMap authHeaders: AuthenticatedHeaders): TaskResponse
}