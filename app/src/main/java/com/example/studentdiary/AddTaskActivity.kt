package com.example.studentdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.DatePicker
import android.widget.Button
import android.content.Intent

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val editTextDescription = findViewById<EditText>(R.id.editTextTaskDescription)
        val datePicker = findViewById<DatePicker>(R.id.datePickerDeadline)
        val btnSaveTask = findViewById<Button>(R.id.btnSaveTask)

        btnSaveTask.setOnClickListener {
            val description = editTextDescription.text.toString()
            val deadlineDay = datePicker.dayOfMonth
            val deadlineMonth = datePicker.month
            val deadlineYear = datePicker.year

            // Здесь сохраняем задачу в базу данных или в список (по мере разработки)
            // Например, можно использовать Room или просто ArrayList для начала

            // После того как задача сохранена, можно вернуться обратно
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Закрываем AddTaskActivity
        }
    }
}
