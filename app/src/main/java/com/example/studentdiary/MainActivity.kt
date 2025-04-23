package com.example.studentdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // установка отступов от системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // обработка кнопки "Открыть календарь"
        val btnOpenCalendar = findViewById<Button>(R.id.btnOpenCalendar)
        btnOpenCalendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        // обработка кнопки "Добавить задачу"
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)  // Убедись, что ты создал AddTaskActivity
            startActivity(intent)
        }

    }
}
