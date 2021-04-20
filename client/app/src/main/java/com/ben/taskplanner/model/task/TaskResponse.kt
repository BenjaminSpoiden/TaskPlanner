package com.ben.taskplanner.model.task

data class TaskResponse(
    val tasks: List<Task>?,
    val error: Any?
)