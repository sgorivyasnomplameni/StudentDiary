package com.example.studentdiary.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import android.app.TimePickerDialog
import android.util.Log
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.studentdiary.R
import com.example.studentdiary.utils.ReminderBroadcastReceiver
import com.example.studentdiary.ui.viewmodels.TaskViewModel
import com.example.studentdiary.data.local.entities.TaskEntity

class AddTaskActivity : AppCompatActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    private var reminderTime: Long? = null // Поле для хранения времени напоминания

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val editTextTitle = findViewById<EditText>(R.id.editTextTaskTitle)
        val editTextDescription = findViewById<EditText>(R.id.editTextTaskDescription)
        val datePicker = findViewById<DatePicker>(R.id.datePickerDeadline)
        val btnSaveTask = findViewById<Button>(R.id.btnSaveTask)
        val btnSetReminder = findViewById<Button>(R.id.btnSetReminder) // Кнопка для выбора времени напоминания

        // Установка текущей даты по умолчанию
        val calendar = Calendar.getInstance()
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )

        // Логика для выбора времени напоминания
        btnSetReminder.setOnClickListener {
            showTimePicker()
        }

        btnSaveTask.setOnClickListener {
            try {
                val selectedCalendar = Calendar.getInstance().apply {
                    set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                // Получаем данные из EditText
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()

                Log.d("AddTask", "Сохранение задачи с датой: ${SimpleDateFormat("dd.MM.yyyy").format(selectedCalendar.time)}")

                val task = TaskEntity(
                    title = title,
                    description = description,
                    dueDate = selectedCalendar.timeInMillis,
                    deadline = selectedCalendar.timeInMillis,
                    reminderTime = reminderTime // Сохраняем время напоминания
                )

                taskViewModel.addTask(task)

                // Планируем напоминание
                scheduleReminder(task)

                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                reminderTime = selectedCalendar.timeInMillis
                Toast.makeText(this, "Напоминание установлено на ${SimpleDateFormat("HH:mm").format(selectedCalendar.time)}", Toast.LENGTH_SHORT).show()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    private fun scheduleReminder(task: TaskEntity) {
        if (task.reminderTime != null) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Интент для BroadcastReceiver
            val intent = Intent(this, ReminderBroadcastReceiver::class.java).apply {
                putExtra("title", task.title)
                putExtra("description", task.description)
            }

            // PendingIntent для AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                task.id.toInt(), // Уникальный requestCode для каждого напоминания
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Устанавливаем будильник
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                task.reminderTime,
                pendingIntent
            )
        }
    }
}