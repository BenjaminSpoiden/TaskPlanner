package com.ben.taskplanner.model

data class TaskModel(val id: Long, val title: String, val isCompleted: Boolean = false, val taskType: TaskType, val date: String)