package com.ben.taskplanner.view

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH: RecyclerView.ViewHolder, T>: RecyclerView.Adapter<VH>() {

    protected val itemList: MutableList<T> = mutableListOf()

    abstract fun addAll(items: List<T>)
    abstract fun add(item: T, position: Int)
    abstract fun removeItem(position: Int)
    abstract fun restoreItem(item: T, position: Int)
}