package com.example.studentdiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlobalGoalViewModel : ViewModel() {

    // Список всех глобальных целей
    private val _globalGoals = MutableLiveData<List<GlobalGoal>>().apply {
        value = listOf(
            GlobalGoal(
                id = 1,
                name = "Прочитать книги",
                targetProgress = 100,
                currentProgress = 0,
                subGoals = listOf(
                    SubGoal(id = 1, name = "1984 (Джордж Оруэлл)", targetProgress = 369, currentProgress = 0),
                    SubGoal(id = 2, name = "Преступление и наказание (Достоевский)", targetProgress = 500, currentProgress = 0)
                )
            ),
            GlobalGoal(
                id = 2,
                name = "Пробежать марафон",
                targetProgress = 50,
                currentProgress = 0,
                subGoals = listOf(
                    SubGoal(id = 3, name = "Пробежать 10 км", targetProgress = 10, currentProgress = 0),
                    SubGoal(id = 4, name = "Пробежать 20 км", targetProgress = 20, currentProgress = 0)
                )
            )
        )
    }
    val globalGoals: LiveData<List<GlobalGoal>> = _globalGoals

    // Текущая выбранная глобальная цель
    private val _currentGlobalGoal = MutableLiveData<GlobalGoal?>()
    val currentGlobalGoal: LiveData<GlobalGoal?> = _currentGlobalGoal

    // Установить текущую глобальную цель
    fun setCurrentGlobalGoal(goalId: Int) {
        val goal = _globalGoals.value?.find { it.id == goalId }
        _currentGlobalGoal.value = goal
    }

    // Обновление прогресса подцели
    fun updateSubGoalProgress(subGoal: SubGoal) {
        val currentGoal = _currentGlobalGoal.value ?: return
        val updatedSubGoals = currentGoal.subGoals.map {
            if (it.id == subGoal.id) subGoal else it
        }
        val updatedCurrentProgress = updatedSubGoals.sumOf { sub ->
            (sub.currentProgress.toFloat() / sub.targetProgress * currentGoal.targetProgress).toInt()
        }

        val updatedGoal = currentGoal.copy(
            subGoals = updatedSubGoals,
            currentProgress = updatedCurrentProgress
        )

        // Обновляем цель в списке глобальных целей
        _globalGoals.value = _globalGoals.value?.map {
            if (it.id == updatedGoal.id) updatedGoal else it
        }
        _currentGlobalGoal.value = updatedGoal
    }
}