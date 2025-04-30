package com.example.studentdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.studentdiary.data.local.entities.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    // Вставка новой цели
    @Insert
    suspend fun insertGoal(goal: GoalEntity)

    // Обновление существующей цели
    @Update
    suspend fun updateGoal(goal: GoalEntity)

    // Удаление цели
    @Delete
    suspend fun deleteGoal(goal: GoalEntity)

    // Получение всех целей
    @Query("SELECT * FROM goals")
    fun getAllGoals(): Flow<List<GoalEntity>>

    // Получение цели по ID
    @Query("SELECT * FROM goals WHERE id = :goalId")
    suspend fun getGoalById(goalId: Int): GoalEntity

    // Получение завершённых целей (где currentProgress >= targetProgress)
    @Query("SELECT * FROM goals WHERE currentProgress >= targetProgress")
    fun getCompletedGoals(): Flow<List<GoalEntity>>

    // Получение активных целей (где currentProgress < targetProgress)
    @Query("SELECT * FROM goals WHERE currentProgress < targetProgress")
    fun getActiveGoals(): Flow<List<GoalEntity>>

    // Поиск целей по имени (или части имени)
    @Query("SELECT * FROM goals WHERE name LIKE '%' || :query || '%'")
    fun searchGoalsByName(query: String): Flow<List<GoalEntity>>

    // Поиск целей по описанию (или части описания)
    @Query("SELECT * FROM goals WHERE description LIKE '%' || :query || '%'")
    fun searchGoalsByDescription(query: String): Flow<List<GoalEntity>>
}