package com.example.studentdiary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()  // Получение DAO из базы данных
    private val taskRepository = TaskRepository(taskDao)  // Репозиторий для работы с DAO

    // Добавление задачи
    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.insert(task)
        }
    }

    // Получение всех задач
    fun getAllTasks(): LiveData<List<TaskEntity>> {
        return taskRepository.getAllTasks().asLiveData()
    }

    // Удаление задачи
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    // Получение задач по дате
    fun getTasksByDate(startOfDay: Long, endOfDay: Long): LiveData<List<TaskEntity>> {
        return taskRepository.getTasksByDate(startOfDay, endOfDay).asLiveData()
    }

    // Получение задач с напоминаниями
    fun getTasksWithReminders(): LiveData<List<TaskEntity>> {
        return taskRepository.getTasksWithReminders().asLiveData()
    }

    // Обновление времени напоминания
    fun updateReminder(taskId: Int, reminderTime: Long?) {
        viewModelScope.launch {
            taskRepository.updateReminder(taskId, reminderTime)
        }
    }

    // Удаление времени напоминания
    fun deleteReminder(taskId: Int) {
        viewModelScope.launch {
            taskRepository.deleteReminder(taskId)
        }
    }

    // Обновление статуса выполнения задачи
    fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTaskCompletion(taskId, isCompleted)
        }
    }
}