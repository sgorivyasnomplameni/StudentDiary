package com.example.studentdiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GoalsViewModel(private val goalDao: GoalDao) : ViewModel() {

    // Все цели
    val allGoals: Flow<List<GoalEntity>> = goalDao.getAllGoals()

    // Завершённые цели
    val completedGoals: Flow<List<GoalEntity>> = goalDao.getCompletedGoals()

    // Активные цели
    val activeGoals: Flow<List<GoalEntity>> = goalDao.getActiveGoals()

    // Добавление новой цели
    fun addGoal(goal: GoalEntity) {
        viewModelScope.launch {
            goalDao.insertGoal(goal)
        }
    }

    // Обновление существующей цели
    fun updateGoal(goal: GoalEntity) {
        viewModelScope.launch {
            goalDao.updateGoal(goal)
        }
    }

    // Удаление цели
    fun deleteGoal(goal: GoalEntity) {
        viewModelScope.launch {
            goalDao.deleteGoal(goal)
        }
    }

    // Поиск целей по имени
    fun searchGoalsByName(query: String): Flow<List<GoalEntity>> {
        return goalDao.searchGoalsByName(query)
    }

    // Поиск целей по описанию
    fun searchGoalsByDescription(query: String): Flow<List<GoalEntity>> {
        return goalDao.searchGoalsByDescription(query)
    }
}