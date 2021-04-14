package com.ben.taskplanner.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ben.taskplanner.util.TaskPlannerDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SharedTokenViewModel @Inject constructor(private val taskPlannerDataStore: TaskPlannerDataStore): ViewModel() {

    fun writeAccessToken(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskPlannerDataStore.saveAccessToken(accessToken)
        }
    }

    fun readAccessToken(): LiveData<String?> {
        return taskPlannerDataStore.readAccessToken.asLiveData(Dispatchers.IO)
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            taskPlannerDataStore.logOut()
        }
    }
}