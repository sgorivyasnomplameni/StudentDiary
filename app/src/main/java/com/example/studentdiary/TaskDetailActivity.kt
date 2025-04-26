package com.example.studentdiary

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels

class TaskDetailActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()  // подключаем ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val taskId = intent.getLongExtra("TASK_ID", -1L)

        val taskDetailTextView: TextView = findViewById(R.id.taskDetailTextView)
        taskDetailTextView.text = "Задача с ID: $taskId"

        val deleteButton: TextView = findViewById(R.id.btnDeleteTask)

        deleteButton.setOnClickListener {
            if (taskId != -1L) {
                val taskToDelete = TaskEntity(
                    id = taskId,
                    title = "",         // пофиг, важен только id для удаления
                    description = null,
                    dueDate = 0L,
                    deadline = 0L
                )
                taskViewModel.deleteTask(taskToDelete)
                finish() // закрыть экран после удаления
            }
        }
    }
}
