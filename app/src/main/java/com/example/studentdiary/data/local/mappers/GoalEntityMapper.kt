package com.example.studentdiary.data.local.mappers

import com.example.studentdiary.data.local.entities.GoalEntity
import com.example.studentdiary.data.model.GlobalGoal

fun GoalEntity.toGlobalGoal(): GlobalGoal {
    return GlobalGoal(
        id = this.id,
        name = this.name,
        targetProgress = this.targetProgress,
        currentProgress = this.currentProgress,
        subGoals = emptyList() // Оставляем пустым, если подцели не используются
    )
}