package com.example.studentdiary.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Уникальный идентификатор цели
    val name: String, // Название цели
    val description: String, // Описание цели
    val targetProgress: Int = 100, // Целевой прогресс (по умолчанию 100%)
    val currentProgress: Int = 0, // Текущий прогресс (по умолчанию 0%)
    val startDate: Long, // Дата начала (в формате timestamp)
    val endDate: Long // Дата окончания (в формате timestamp)
)