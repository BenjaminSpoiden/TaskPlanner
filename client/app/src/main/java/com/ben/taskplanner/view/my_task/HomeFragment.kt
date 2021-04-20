package com.ben.taskplanner.view.my_task

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.MainActivity
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentHomeBinding
import com.ben.taskplanner.interfaces.RecyclerItemTouchHelperListener
import com.ben.taskplanner.interfaces.RemoveItemListener
import com.ben.taskplanner.model.*
import com.ben.taskplanner.model.task.Task
import com.ben.taskplanner.util.RecyclerItemTouchHelper
import com.ben.taskplanner.view.BaseFragment
import com.ben.taskplanner.view.SharedTokenViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MyTaskViewModel>(),
    RemoveItemListener<Task> {

    private val taskRecyclerView: RecyclerView by lazy { binding.todayTaskRv }
    private val dayRecyclerView: RecyclerView by lazy { binding.dayRv }

    private val taskRecyclerViewAdapter: TaskRecyclerViewAdapter by lazy { TaskRecyclerViewAdapter() }
    private val dayRecyclerViewAdapter: DayRecyclerViewAdapter by lazy { DayRecyclerViewAdapter() }

    private val sharedTokenViewModel: SharedTokenViewModel by lazy {
        ViewModelProvider(this).get(SharedTokenViewModel::class.java)
    }

    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    override fun bindViewModel(): Class<MyTaskViewModel> = MyTaskViewModel::class.java

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedTokenViewModel.readAccessToken().observe(viewLifecycleOwner) { accessToken ->
            accessToken?.let {
                viewModel.fetchTasks(it)
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseHandler.Loading -> {
                    Log.d("Tag", "Loading...")
                }
                is ResponseHandler.SuccessResponse -> {
                    it.response.tasks?.let { tasks -> taskRecyclerViewAdapter.addAll(tasks) }
                }
                is ResponseHandler.FailureResponse -> {
                    Log.d("Tag", "${it.responseBody?.string()}")
                }
            }
        }


        setHasOptionsMenu(true)
        setupRecyclerView()
        dayRecyclerViewAdapter.addAll(daysMockData())

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }


        TaskRecyclerViewAdapter.onItemClickListener = {
            Log.d("Tag", "$it")
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateTaskFragment(it)
            findNavController().navigate(action)
        }

        TaskRecyclerViewAdapter.removeItemListener = this
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.filterList -> {
                findNavController().navigate(R.id.action_homeFragment_to_filterListDialogFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setupRecyclerView() {
        dayRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            this.adapter = dayRecyclerViewAdapter
        }
        taskRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = taskRecyclerViewAdapter
        }
    }

    private fun daysMockData() = listOf(
        Day("M", 12),
        Day("T", 13),
        Day("W", 14),
        Day("T", 15),
        Day("F", 16),
        Day("S", 17),
        Day("S", 18),
        Day("L", 19),
        Day("T", 20),
    )

    override fun onItemRemoved(deletedItem: Task, position: Int) {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
        Snackbar.make(requireView(),"${deletedItem.title} removed", Snackbar.LENGTH_LONG)
            .setAnchorView(bottomNav)
            .setAction("UNDO") {
                taskRecyclerViewAdapter.restoreItem(deletedItem, position)
            }.show()
    }
}