package com.example.studentdiary

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [TaskEntity::class], version = 3, exportSchema = false) // Увеличена версия базы данных
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Добавлены миграции
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Миграция с версии 1 на 2: добавляем столбец deadline
        private val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN deadline INTEGER NOT NULL DEFAULT 0")
            }
        }

        // Миграция с версии 2 на 3: добавляем столбец reminderTime
        private val MIGRATION_2_3 = object : androidx.room.migration.Migration(2, 3) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN reminderTime INTEGER")
            }
        }
    }
}