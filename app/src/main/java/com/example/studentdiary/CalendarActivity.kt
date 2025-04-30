package com.example.studentdiary

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initUI()
        setupRecyclerView()
        setupCalendar()
        setupBackButton()
    }

    /**
     * Инициализация пользовательского интерфейса
     */
    private fun initUI() {
        taskAdapter = TaskAdapter(emptyList()) { _, _ -> }
    }

    /**
     * Настраиваем RecyclerView для отображения задач
     */
    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
    }

    /**
     * Настройка календаря и обработка выбора даты
     */
    private fun setupCalendar() {
        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        // Получение дат с напоминаниями
        taskViewModel.getTasksWithReminders().observe(this) { tasks ->
            val reminderDates = tasks.map { it.dueDate }
            val calendarDecorator = CalendarDecorator(this, calendarView, reminderDates)
            calendarDecorator.invalidate()
        }

        // Обработчик выбора даты
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            handleDateSelection(year, month, dayOfMonth)
        }
    }

    /**
     * Обрабатываем выбор даты в календаре
     */
    private fun handleDateSelection(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + 86400000 - 1 // Конец дня

        taskViewModel.getTasksByDate(startOfDay, endOfDay).observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
            if (tasks.isEmpty()) {
                Toast.makeText(
                    this,
                    "На ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(startOfDay)} задач нет",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Настройка кнопки "Назад"
     */
    private fun setupBackButton() {
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            // Возвращаем результат в MainActivity
            val intent = Intent()
            intent.putExtra("selectedMenuItem", R.id.goalsListFragment) // Обновлено на корректный ID
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}