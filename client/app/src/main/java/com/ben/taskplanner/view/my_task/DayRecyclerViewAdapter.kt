package com.ben.taskplanner.view.my_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ben.taskplanner.databinding.DayItemBinding
import com.ben.taskplanner.model.Day
import com.ben.taskplanner.view.BaseAdapter

class DayRecyclerViewAdapter: BaseAdapter<DayRecyclerViewAdapter.DayViewHolder, Day>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun addAll(items: List<Day>) {
        itemList.addAll(items)
    }

    override fun add(item: Day, position: Int) {

    }

    override fun removeItem(position: Int) {

    }

    override fun restoreItem(item: Day, position: Int) {

    }

    override fun getItemCount(): Int = itemList.size

    class DayViewHolder private constructor(private val binding: DayItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DayViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = DayItemBinding.inflate(layoutInflater, parent, false)
                return DayViewHolder(view)
            }
        }

        fun bind(day: Day) {
            binding.day.text = day.day
            binding.dayNumber.text = day.dayNumber.toString()
        }
    }
}