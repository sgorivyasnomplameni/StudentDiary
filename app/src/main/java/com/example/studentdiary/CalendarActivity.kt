package com.example.studentdiary

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var calendarDecorator: CalendarDecorator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val recyclerView = findViewById<RecyclerView>(R.id.rvTasks)

        // Настройка RecyclerView
        taskAdapter = TaskAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        // Получение дат с напоминаниями
        taskViewModel.getTasksWithReminders().observe(this) { tasks ->
            val reminderDates = tasks.map { it.dueDate }
            calendarDecorator = CalendarDecorator(this, calendarView, reminderDates)
            calendarDecorator.invalidate() // Перерисовать индикаторы
        }

        // Обработчик выбора даты в календаре
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfDay = calendar.timeInMillis
            val endOfDay = startOfDay + 86400000 - 1 // Конец дня

            taskViewModel.getTasksByDate(startOfDay, endOfDay).observe(this) { tasks ->
                taskAdapter.updateTasks(tasks)
                if (tasks.isEmpty()) {
                    Toast.makeText(
                        this,
                        "На ${SimpleDateFormat("dd.MM.yyyy").format(startOfDay)} задач нет",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnBack.setOnClickListener { finish() }
    }
}