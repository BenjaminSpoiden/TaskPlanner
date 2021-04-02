package com.ben.taskplanner.view.my_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ben.taskplanner.databinding.FragmentHomeBinding
import com.ben.taskplanner.view.BaseFragment
import com.ben.taskplanner.view.my_task.task_pager_fragments.ThisMonthTaskFragment
import com.ben.taskplanner.view.my_task.task_pager_fragments.ThisWeekTaskFragment
import com.ben.taskplanner.view.my_task.task_pager_fragments.TodayTaskFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun bindFragment(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {

        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = mutableListOf(
            TodayTaskFragment.newInstance(),
            ThisWeekTaskFragment.newInstance(),
            ThisMonthTaskFragment.newInstance()
        )
        val taskViewPager = binding.tasksViewPager
        val taskViewPagerAdapter = TaskViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        taskViewPager.adapter = taskViewPagerAdapter
        TabLayoutMediator(binding.tabLayout2, taskViewPager) { tab, position ->
           when(position) {
               0 -> tab.text = "Today"
               1 -> tab.text = "This Week"
               2 -> tab.text = "This Month"
           }
        }.attach()
    }
}