package com.example.studentdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.studentdiary.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Вставка задачи
    @Insert
    suspend fun insert(task: TaskEntity)

    // Получение всех задач с сортировкой по ID
    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // Удаление задачи
    @Delete
    suspend fun delete(task: TaskEntity)

    // Получение задач по дате
    @Query("""
        SELECT * FROM tasks 
        WHERE dueDate BETWEEN :startOfDay AND :endOfDay 
        ORDER BY id ASC
    """)
    fun getTasksByDate(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>>

    // Получение задач с напоминаниями
    @Query("SELECT * FROM tasks WHERE reminderTime IS NOT NULL ORDER BY id ASC")
    fun getTasksWithReminders(): Flow<List<TaskEntity>>

    // Обновление времени напоминания
    @Query("UPDATE tasks SET reminderTime = :reminderTime WHERE id = :taskId")
    suspend fun updateReminder(taskId: Int, reminderTime: Long?)

    // Удаление времени напоминания
    @Query("UPDATE tasks SET reminderTime = NULL WHERE id = :taskId")
    suspend fun deleteReminder(taskId: Int)

    // Обновление статуса выполнения задачи
    @Query("UPDATE tasks SET completed = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean)
}