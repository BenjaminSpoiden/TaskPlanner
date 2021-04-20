package com.ben.taskplanner.interfaces

interface NumPadListener {
    fun onNumberClicked(number: Char)
    fun onErased()
    fun onValidate()
}