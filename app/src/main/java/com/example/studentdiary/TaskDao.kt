package com.example.studentdiary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: TaskEntity)  // Вставка задачи

    // Получение всех задач с сортировкой по deadline
    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Delete
    suspend fun delete(task: TaskEntity)

    // Добавьте этот метод ↓ (выборка задач по дате)
    @Query("""
    SELECT * FROM tasks 
    WHERE dueDate BETWEEN :startOfDay AND :endOfDay 
    ORDER BY id ASC
""")
    fun getTasksByDate(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>>
}
