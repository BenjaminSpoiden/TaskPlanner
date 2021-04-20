package com.ben.taskplanner.interfaces

interface RemoveItemListener <T> {
    fun onItemRemoved(deletedItem: T, position: Int)
}