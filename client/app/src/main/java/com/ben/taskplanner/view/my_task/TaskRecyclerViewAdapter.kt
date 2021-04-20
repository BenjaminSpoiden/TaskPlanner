package com.ben.taskplanner.view.my_task

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.TodayTaskItemBinding
import com.ben.taskplanner.interfaces.RecyclerItemTouchHelperListener
import com.ben.taskplanner.interfaces.RemoveItemListener
import com.ben.taskplanner.model.Priority
import com.ben.taskplanner.model.TaskModel
import com.ben.taskplanner.model.task.Task
import com.ben.taskplanner.util.RecyclerItemTouchHelper
import com.ben.taskplanner.view.BaseAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskRecyclerViewAdapter: BaseAdapter<TaskRecyclerViewAdapter.TaskRecyclerViewHolder, Task>(), RecyclerItemTouchHelperListener {

    companion object {
        var onItemClickListener: ((item: Task) -> Unit)? = null
        var removeItemListener: RemoveItemListener<Task>? = null
    }

    private val itemTouchHelper = ItemTouchHelper(swipeGesturesForRecyclerView())
    private val adapterScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder = TaskRecyclerViewHolder.bind(parent, parent.context)


    override fun onBindViewHolder(holder: TaskRecyclerViewHolder, position: Int) {
        holder.bind(itemList[position])
    }


    override fun addAll(items: List<Task>) {
        adapterScope.launch {
            val groupedList = items.groupBy { it.createdAt }.flatMap { it.value }
            Log.d("Tag", "List: $groupedList")
            withContext(Dispatchers.Main) {
                itemList.addAll(groupedList)
                notifyDataSetChanged()
            }
        }
    }

    override fun add(item: Task, position: Int) {
        this.itemList.add(item)
        notifyItemInserted(position)
    }

    override fun removeItem(position: Int) {
        this.itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun restoreItem(item: Task, position: Int) {
        this.itemList.add(position, item)
        notifyItemInserted(position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = itemList.size

    private fun swipeGesturesForRecyclerView() = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)



    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        viewHolder?.let {
            val deletedItem = itemList[viewHolder.adapterPosition]
            val removedIndex = viewHolder.adapterPosition
            removeItem(viewHolder.adapterPosition).also {
                removeItemListener?.onItemRemoved(deletedItem, removedIndex)
            }
        }
    }

    class TaskRecyclerViewHolder private constructor(private val binding: TodayTaskItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun bind(parent: ViewGroup, context: Context): TaskRecyclerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = TodayTaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskRecyclerViewHolder(view, context)
            }
        }

        val viewForeground = binding.foregroundView

        fun bind(taskItem: Task) {
            binding.taskTitle.text = taskItem.title
            binding.taskDesc.text = taskItem.description
            binding.priorityIndicator.imageTintList = ColorStateList.valueOf(Color.parseColor(Priority.valueOf("LOW").color))
            binding.taskTime.text = taskItem.createdAt
            binding.taskState.setOnCheckedChangeListener { _, isChecked ->
                taskState(isChecked)
            }

            binding.cardRoot.setOnClickListener {
                onItemClickListener?.invoke(taskItem)
            }
        }

        private fun taskState(isCompleted: Boolean) {
            binding.taskState.isChecked = isCompleted
            binding.taskState.buttonTintList =
                    if (isCompleted)
                        ColorStateList.valueOf(context.resources.getColor(R.color.red_ish, context.theme))
                    else
                        ColorStateList.valueOf(context.resources.getColor(R.color.shade_of_blue, context.theme))

            binding.taskTitle.paintFlags = if(isCompleted) Paint.STRIKE_THRU_TEXT_FLAG else 0
            binding.taskTitle.setTextColor(
                    if(isCompleted)
                        ContextCompat.getColor(context, R.color.light_gray)
                    else
                        ContextCompat.getColor(context, R.color.black)
            )

            binding.taskDesc.paintFlags = if(isCompleted) Paint.STRIKE_THRU_TEXT_FLAG else 0
            binding.taskDesc.setTextColor(
                if(isCompleted)
                    ContextCompat.getColor(context, R.color.light_gray)
                else
                    ContextCompat.getColor(context, R.color.black)
            )

            binding.taskTime.paintFlags = if(isCompleted) Paint.STRIKE_THRU_TEXT_FLAG else 0

            binding.cardDecorator.setBackgroundColor(
                    if(isCompleted)
                        ContextCompat.getColor(context, R.color.red_ish)
                    else
                        ContextCompat.getColor(context, R.color.shade_of_blue)
            )
        }
    }
}