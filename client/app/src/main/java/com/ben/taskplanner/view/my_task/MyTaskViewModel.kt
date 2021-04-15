package com.ben.taskplanner.view.my_task

import androidx.lifecycle.ViewModel
import com.ben.taskplanner.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyTaskViewModel @Inject constructor(
    private val authRepositoryFactory: AuthRepository
): ViewModel() {

}