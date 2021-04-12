package com.ben.taskplanner.network

import com.ben.taskplanner.model.user.CreateUserModel
import com.ben.taskplanner.model.user.LoginUserModel
import com.ben.taskplanner.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskPlannerService {

    // *** -- User Requests -- ***
    @POST("/register")
    suspend fun onCreateUser(@Body userRequest: CreateUserModel): UserResponse
    @POST("/login")
    suspend fun onLoginUser(@Body userRequest: LoginUserModel): UserResponse

}