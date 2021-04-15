package com.ben.taskplanner.model.user

data class User(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val avatar: String?,
    val surname: String,
    val createdAt: String,
    val updatedAt: String
)