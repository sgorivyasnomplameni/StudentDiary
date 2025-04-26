package com.example.studentdiary

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import android.util.Log

class AddTaskActivity : AppCompatActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val editTextTitle = findViewById<EditText>(R.id.editTextTaskTitle)
        val editTextDescription = findViewById<EditText>(R.id.editTextTaskDescription)
        val datePicker = findViewById<DatePicker>(R.id.datePickerDeadline)
        val btnSaveTask = findViewById<Button>(R.id.btnSaveTask)

        // Установка текущей даты по умолчанию
        val calendar = Calendar.getInstance()
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )

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
                    deadline = selectedCalendar.timeInMillis
                )

                taskViewModel.addTask(task)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
