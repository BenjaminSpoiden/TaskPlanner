package com.ben.taskplanner.di

import com.ben.taskplanner.TaskPlannerRepository
import com.ben.taskplanner.network.TaskPlannerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object TaskPlannerRepositoryModule {

    @Provides
    @Singleton
    fun provideTaskPlannerRepository(taskPlannerService: TaskPlannerService): TaskPlannerRepository {
        return TaskPlannerRepository(taskPlannerService)
    }
}