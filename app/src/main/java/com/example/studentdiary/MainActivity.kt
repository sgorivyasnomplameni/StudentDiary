package com.example.studentdiary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private var isFiltered = false // Переменная для отслеживания состояния фильтрации

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        // Установка отступов для системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList())
        recyclerView.adapter = taskAdapter

        // Наблюдение за задачами и обновление адаптера
        taskViewModel.getAllTasks().observe(this, { tasks ->
            if (!isFiltered) { // Обновляем только если режим не фильтрации
                taskAdapter.updateTasks(tasks)
            }
        })

        // Добавляем обработчик кликов для элементов списка
        taskAdapter.setOnItemClickListener { task ->
            // Логируем информацию о задаче
            Log.d("MainActivity", "Task clicked: ID = ${task.id}, Title = ${task.title}")

            // Открываем TaskDetailActivity при клике на задачу
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("TASK_ID", task.id.toLong()) // Передаем ID задачи
            Log.d("MainActivity", "Launching TaskDetailActivity with Task ID: ${task.id}")
            startActivity(intent)
        }

        // Кнопка для открытия календаря
        val btnOpenCalendar = findViewById<Button>(R.id.btnOpenCalendar)
        btnOpenCalendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        // Кнопка для добавления новой задачи
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        // Кнопка для фильтрации задач с напоминаниями
        val btnFilterReminders = findViewById<Button>(R.id.btnFilterReminders)
        btnFilterReminders.setOnClickListener {
            isFiltered = !isFiltered // Переключаем состояние фильтрации

            if (isFiltered) {
                // Отображаем только задачи с напоминаниями
                taskViewModel.getTasksWithReminders().observe(this, { tasks ->
                    taskAdapter.updateTasks(tasks)
                })
                btnFilterReminders.text = "Показать все задачи" // Меняем текст кнопки
            } else {
                // Отображаем все задачи
                taskViewModel.getAllTasks().observe(this, { tasks ->
                    taskAdapter.updateTasks(tasks)
                })
                btnFilterReminders.text = "Фильтровать по напоминаниям" // Меняем текст кнопки
            }
        }
    }

    private fun showReminderDialog(task: TaskEntity) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Управление напоминанием")

        val input = EditText(this)
        input.hint = "Введите время напоминания (в миллисекундах)"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        input.setText(task.reminderTime?.toString() ?: "")

        dialog.setView(input)

        dialog.setPositiveButton("Обновить") { _, _ ->
            val newReminderTime = input.text.toString().toLongOrNull()
            if (newReminderTime != null) {
                taskViewModel.updateReminder(task.id, newReminderTime)
                Toast.makeText(this, "Напоминание обновлено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Некорректное время", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setNegativeButton("Удалить") { _, _ ->
            taskViewModel.deleteReminder(task.id)
            Toast.makeText(this, "Напоминание удалено", Toast.LENGTH_SHORT).show()
        }

        dialog.setNeutralButton("Отмена", null)

        dialog.show()
    }
}