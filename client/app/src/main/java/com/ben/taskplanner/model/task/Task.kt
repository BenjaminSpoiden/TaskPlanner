package com.ben.taskplanner.model.task


import android.os.Parcelable
import com.ben.taskplanner.model.Priority
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("creator_id")
    val creatorId: String,
    val description: String,
    val id: Int,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    val name: String,
    val priority: String,
    val surname: String,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
): Parcelable