package com.ben.taskplanner.view.my_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.databinding.TodayTaskItemBinding
import com.ben.taskplanner.model.TaskModel

class TaskRecyclerViewAdapter: RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskRecyclerViewHolder>() {

    private val taskList: MutableList<TaskModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder = TaskRecyclerViewHolder.bind(parent)

    override fun onBindViewHolder(holder: TaskRecyclerViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    fun addAll(taskList: List<TaskModel>) {
        this.taskList.addAll(taskList)
        notifyDataSetChanged()
    }

    fun add(taskItem: TaskModel, position: Int) {
        this.taskList.add(taskItem)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        this.taskList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(taskItem: TaskModel, position: Int) {
        this.taskList.add(position, taskItem)
        notifyItemInserted(position)
    }

    override fun getItemCount(): Int = taskList.size

    class TaskRecyclerViewHolder private constructor(private val binding: TodayTaskItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun bind(parent: ViewGroup): TaskRecyclerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = TodayTaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskRecyclerViewHolder(view)
            }
        }

        val viewForeground = binding.foregroundView

        fun bind(taskItem: TaskModel) {
            binding.taskTitle.text = taskItem.title
            binding.taskTime.text = taskItem.date
        }
    }
}