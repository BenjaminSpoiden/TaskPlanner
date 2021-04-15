package com.ben.taskplanner.network

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

}