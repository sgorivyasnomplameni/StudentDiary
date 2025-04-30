package com.example.studentdiary

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val targetProgress: Int, // Целевой прогресс, например, 100%
    val currentProgress: Int, // Текущий прогресс
    val startDate: Date,
    val endDate: Date
)