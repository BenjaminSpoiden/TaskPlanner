package com.ben.taskplanner.view.my_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.repository.TaskPlannerRepository
import com.ben.taskplanner.util.TaskPlannerDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTaskViewModel @Inject constructor(
    private val taskPlannerRepositoryFactory: TaskPlannerRepository
): ViewModel() {

}