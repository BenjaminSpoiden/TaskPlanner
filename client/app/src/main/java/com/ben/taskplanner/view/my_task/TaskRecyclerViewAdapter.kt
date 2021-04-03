package com.ben.taskplanner.view.my_task

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.TodayTaskItemBinding
import com.ben.taskplanner.model.TaskModel

class TaskRecyclerViewAdapter: RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskRecyclerViewHolder>() {

    private val taskList: MutableList<TaskModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder = TaskRecyclerViewHolder.bind(parent, parent.context)

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

    class TaskRecyclerViewHolder private constructor(private val binding: TodayTaskItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun bind(parent: ViewGroup, context: Context): TaskRecyclerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = TodayTaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskRecyclerViewHolder(view, context)
            }
        }

        val viewForeground = binding.foregroundView

        fun bind(taskItem: TaskModel) {
            binding.taskTitle.text = taskItem.title
            binding.taskTime.text = taskItem.date
            taskState(taskItem.isCompleted)
        }

        private fun taskState(isCompleted: Boolean) {
            binding.taskState.isChecked = isCompleted
            binding.taskState.buttonTintList =
                    if (isCompleted)
                        ColorStateList.valueOf(context.resources.getColor(R.color.red_ish, context.theme))
                    else
                        ColorStateList.valueOf(context.resources.getColor(R.color.shade_of_blue, context.theme))

            binding.taskTitle.paintFlags = if(isCompleted) Paint.STRIKE_THRU_TEXT_FLAG else binding.taskTitle.paintFlags
            binding.taskTitle.setTextColor(
                    if(isCompleted)
                        ContextCompat.getColor(context, R.color.light_gray)
                    else
                        ContextCompat.getColor(context, R.color.black)
            )
            binding.cardDecorator.setBackgroundColor(
                    if(isCompleted)
                        ContextCompat.getColor(context, R.color.red_ish)
                    else
                        ContextCompat.getColor(context, R.color.shade_of_blue)
            )
        }
    }
}