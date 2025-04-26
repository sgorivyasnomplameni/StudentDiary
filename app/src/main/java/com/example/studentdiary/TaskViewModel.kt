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

    // Функция для добавления задачи
    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.insert(task)  // Вставка задачи в базу данных
        }
    }

    fun getTasksByDate(startOfDay: Long, endOfDay: Long): LiveData<List<TaskEntity>> {
        return taskRepository.getTasksByDate(startOfDay, endOfDay).asLiveData()
    }

    // Функция для получения всех задач, преобразованных в LiveData
    fun getAllTasks(): LiveData<List<TaskEntity>> {
        return taskRepository.getAllTasks().asLiveData()  // Преобразование Flow в LiveData
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

}