package com.example.studentdiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GoalsViewModel(private val goalDao: GoalDao) : ViewModel() {

    val allGoals: Flow<List<GoalEntity>> = goalDao.getAllGoals()

    fun addGoal(goal: GoalEntity) {
        viewModelScope.launch {
            goalDao.insertGoal(goal)
        }
    }

    fun updateGoal(goal: GoalEntity) {
        viewModelScope.launch {
            goalDao.updateGoal(goal)
        }
    }
}