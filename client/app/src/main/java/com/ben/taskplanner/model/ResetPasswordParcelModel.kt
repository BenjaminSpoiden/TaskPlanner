package com.ben.taskplanner.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResetPasswordParcelModel(val email: String, val token: String): Parcelable