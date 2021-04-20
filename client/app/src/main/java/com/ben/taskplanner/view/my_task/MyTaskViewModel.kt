package com.ben.taskplanner.view.my_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.model.task.TaskResponse
import com.ben.taskplanner.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _tasks: MutableLiveData<ResponseHandler<TaskResponse>> = MutableLiveData()
    val tasks: LiveData<ResponseHandler<TaskResponse>> get() = _tasks

    fun fetchTasks(accessToken: String) {
        viewModelScope.launch {
            _tasks.value = ResponseHandler.Loading
            _tasks.value = taskRepository.fetchTasks(accessToken)
        }
    }
}