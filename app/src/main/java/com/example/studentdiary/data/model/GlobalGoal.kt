package com.example.studentdiary.data.model

data class GlobalGoal(
    val id: Int,
    val name: String,
    val targetProgress: Int, // Целевой прогресс (например, 100 страниц)
    var currentProgress: Int, // Текущий прогресс
    val subGoals: List<SubGoal> // Список подцелей
)