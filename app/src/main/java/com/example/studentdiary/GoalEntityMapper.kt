package com.example.studentdiary

fun GoalEntity.toGlobalGoal(): GlobalGoal {
    return GlobalGoal(
        id = this.id,
        name = this.name,
        targetProgress = this.targetProgress,
        currentProgress = this.currentProgress,
        subGoals = emptyList() // Оставляем пустым, если подцели не используются
    )
}