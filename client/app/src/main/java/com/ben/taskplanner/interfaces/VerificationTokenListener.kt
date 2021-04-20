package com.ben.taskplanner.interfaces

interface VerificationTokenListener {
    fun onValidate(token: String)
}