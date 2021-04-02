package com.ben.taskplanner.view.my_task.task_pager_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentTodayTaskBinding
import com.ben.taskplanner.interfaces.RecyclerItemTouchHelperListener
import com.ben.taskplanner.model.TaskModel
import com.ben.taskplanner.model.TaskType
import com.ben.taskplanner.util.RecyclerItemTouchHelper
import com.ben.taskplanner.view.BaseFragment
import com.ben.taskplanner.view.my_task.TaskRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar


class TodayTaskFragment : BaseFragment<FragmentTodayTaskBinding>(), RecyclerItemTouchHelperListener {

    private val taskRecyclerView: RecyclerView by lazy { binding.todayTaskRv }
    private val taskRecyclerViewAdapter: TaskRecyclerViewAdapter by lazy { TaskRecyclerViewAdapter() }

    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTodayTaskBinding {
        return FragmentTodayTaskBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        taskRecyclerViewAdapter.addAll(mockData())
    }

    companion object {
        @JvmStatic
        fun newInstance() = TodayTaskFragment()
    }

    private fun setupRecyclerView() {
        val itemTouchHelper = ItemTouchHelper(swipeGesturesForRecyclerView())
        taskRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = taskRecyclerViewAdapter
        }
        itemTouchHelper.attachToRecyclerView(taskRecyclerView)
    }

    private fun mockData() = listOf(
        TaskModel(0, "Rendez-vous with Joe", false, TaskType.MEETING, "9h"),
        TaskModel(1, "Rendez-vous with Joe", false, TaskType.MEETING, "9h"),
        TaskModel(2, "Rendez-vous with Joe", true, TaskType.MEETING, "9h"),
        TaskModel(3, "Rendez-vous with Joe", false, TaskType.MEETING, "9h"),
        TaskModel(4, "Rendez-vous with Joe", false, TaskType.MEETING, "9h")
    )

    private fun swipeGesturesForRecyclerView() = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if(viewHolder is TaskRecyclerViewAdapter.TaskRecyclerViewHolder) {
            val name = mockData()[viewHolder.adapterPosition].title

            val deletedItem = mockData()[viewHolder.adapterPosition]
            val removedIndex = viewHolder.adapterPosition

            taskRecyclerViewAdapter.removeItem(viewHolder.adapterPosition)

            Snackbar.make(requireView(),"$name removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    taskRecyclerViewAdapter.restoreItem(deletedItem, removedIndex)
                }.show()
        }
    }

}