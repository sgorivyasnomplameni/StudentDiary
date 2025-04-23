package com.example.studentdiary

data class Task(
    val id: Int,           // Уникальный идентификатор задачи
    val title: String,     // Название задачи
    val description: String, // Описание задачи
    val dueDate: String    // Дата выполнения задачи
)