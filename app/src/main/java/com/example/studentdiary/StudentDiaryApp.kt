package com.example.studentdiary

import android.app.Application

class StudentDiaryApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        // Здесь можно добавить любую инициализацию, если потребуется
    }
}