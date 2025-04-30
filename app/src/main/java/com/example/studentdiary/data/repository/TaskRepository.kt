package com.example.studentdiary.data.repository

import com.example.studentdiary.data.local.dao.TaskDao
import com.example.studentdiary.data.local.entities.TaskEntity
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

    // Удаление задачи
    suspend fun delete(task: TaskEntity) {
        taskDao.delete(task)
    }

    // Получение задач по дате
    fun getTasksByDate(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>> {
        return taskDao.getTasksByDate(startOfDay, endOfDay)
    }

    // Получение задач с напоминаниями
    fun getTasksWithReminders(): Flow<List<TaskEntity>> {
        return taskDao.getTasksWithReminders()
    }

    // Обновление времени напоминания
    suspend fun updateReminder(taskId: Int, reminderTime: Long?) {
        taskDao.updateReminder(taskId, reminderTime)
    }

    // Удаление времени напоминания
    suspend fun deleteReminder(taskId: Int) {
        taskDao.deleteReminder(taskId)
    }

    // Обновление статуса выполнения задачи
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) {
        taskDao.updateTaskCompletion(taskId, isCompleted)
    }
}