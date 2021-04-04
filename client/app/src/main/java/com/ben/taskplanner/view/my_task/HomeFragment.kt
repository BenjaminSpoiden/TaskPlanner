package com.ben.taskplanner.view.my_task

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentHomeBinding
import com.ben.taskplanner.interfaces.RecyclerItemTouchHelperListener
import com.ben.taskplanner.model.TaskModel
import com.ben.taskplanner.model.TaskType
import com.ben.taskplanner.util.RecyclerItemTouchHelper
import com.ben.taskplanner.view.BaseFragment
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeFragment : BaseFragment<FragmentHomeBinding>(), RecyclerItemTouchHelperListener {

    private val taskRecyclerView: RecyclerView by lazy { binding.todayTaskRv }
    private val taskRecyclerViewAdapter: TaskRecyclerViewAdapter by lazy { TaskRecyclerViewAdapter() }

    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()
//        taskRecyclerViewAdapter.addAll(mockData())

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_updateTaskFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    private fun setupRecyclerView() {
        val itemTouchHelper = ItemTouchHelper(swipeGesturesForRecyclerView())
        taskRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = taskRecyclerViewAdapter
        }
        itemTouchHelper.attachToRecyclerView(taskRecyclerView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mockData() = listOf(
            TaskModel(0, "Rendez-vous with Joe", false, TaskType.MEETING, now()),
            TaskModel(1, "Rendez-vous with Joe", false, TaskType.MEETING, now()),
            TaskModel(2, "Rendez-vous with Joe", true, TaskType.MEETING, now()),
            TaskModel(3, "Rendez-vous with Joe", false, TaskType.MEETING, now()),
            TaskModel(4, "Rendez-vous with Joe", false, TaskType.MEETING, now())
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun now() = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

    private fun swipeGesturesForRecyclerView() = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)

    @RequiresApi(Build.VERSION_CODES.O)
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