package com.example.studentdiary

import android.app.Application
import com.example.studentdiary.data.local.AppDatabase

class StudentDiaryApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        // Здесь можно добавить любую инициализацию, если потребуется
    }
}