package com.example.studentdiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlobalGoalViewModel : ViewModel() {

    // Пример данных (можно заменить на загрузку из БД)
    private val _globalGoal = MutableLiveData<GlobalGoal>().apply {
        value = GlobalGoal(
            id = 1,
            name = "Прочитать книги",
            targetProgress = 100,
            currentProgress = 0,
            subGoals = listOf(
                SubGoal(id = 1, name = "1984 (Джордж Оруэлл)", targetProgress = 369, currentProgress = 0),
                SubGoal(id = 2, name = "Преступление и наказание (Достоевский)", targetProgress = 500, currentProgress = 0)
            )
        )
    }
    val globalGoal: LiveData<GlobalGoal> = _globalGoal

    // Обновление прогресса подцели
    fun updateSubGoalProgress(subGoal: SubGoal) {
        val currentGoal = _globalGoal.value ?: return
        val updatedSubGoals = currentGoal.subGoals.map {
            if (it.id == subGoal.id) subGoal else it
        }
        val updatedCurrentProgress = updatedSubGoals.sumOf { sub ->
            (sub.currentProgress.toFloat() / sub.targetProgress * currentGoal.targetProgress).toInt()
        }

        _globalGoal.value = currentGoal.copy(
            subGoals = updatedSubGoals,
            currentProgress = updatedCurrentProgress
        )
    }
}