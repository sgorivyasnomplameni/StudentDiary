package com.example.studentdiary

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // Вставка задачи
    suspend fun insert(task: TaskEntity) {
        taskDao.insert(task)
    }

    // Получение всех задач
    fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    suspend fun delete(task: TaskEntity) {
        taskDao.delete(task)
    }

    fun getTasksByDate(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>> {
        return taskDao.getTasksByDate(startOfDay, endOfDay)
    }

}
