package com.example.studentdiary.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.studentdiary.data.local.converters.DateConverters
import com.example.studentdiary.data.local.dao.GoalDao
import com.example.studentdiary.data.local.dao.TaskDao
import com.example.studentdiary.data.local.entities.GoalEntity
import com.example.studentdiary.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class, GoalEntity::class], version = 4, exportSchema = false) // Увеличена версия
@TypeConverters(DateConverters::class) // Подключение конвертеров
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun goalDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_diary_database"
                )
                    .fallbackToDestructiveMigration() // Удаляет старую базу
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}