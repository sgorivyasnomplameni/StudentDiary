package com.example.studentdiary.data.model

data class SubGoal(
    val id: Int,
    val name: String,
    val targetProgress: Int, // Целевой прогресс подцели
    var currentProgress: Int // Текущий прогресс подцели
)