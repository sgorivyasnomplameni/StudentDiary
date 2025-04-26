package com.example.studentdiary

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar


class AddTaskActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val editTextDescription = findViewById<EditText>(R.id.editTextTaskDescription)
        val datePicker = findViewById<DatePicker>(R.id.datePickerDeadline)
        val btnSaveTask = findViewById<Button>(R.id.btnSaveTask)

        btnSaveTask.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }

            val task = TaskEntity(
                title = "New Task",
                description = editTextDescription.text.toString(),
                dueDate = calendar.timeInMillis,
                deadline = calendar.timeInMillis
            )

            taskViewModel.addTask(task)
            finish()
        }
    }
}
