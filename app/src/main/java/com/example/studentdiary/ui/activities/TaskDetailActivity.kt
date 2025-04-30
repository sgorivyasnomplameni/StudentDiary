package com.example.studentdiary.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.studentdiary.R
import com.example.studentdiary.ui.viewmodels.TaskViewModel
import com.example.studentdiary.data.local.entities.TaskEntity

class TaskDetailActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()  // подключаем ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        // Получаем ID задачи из Intent
        val taskId = intent.getLongExtra("TASK_ID", -1L)
        Log.d("TaskDetailActivity", "Received Task ID: $taskId")

        val taskDetailTextView: TextView = findViewById(R.id.taskDetailTextView)
        taskDetailTextView.text = "Задача с ID: $taskId"

        val deleteButton: TextView = findViewById(R.id.btnDeleteTask)

        deleteButton.setOnClickListener {
            if (taskId != -1L) {
                Log.d("TaskDetailActivity", "Attempting to delete task with ID: $taskId")

                // Создаем объект TaskEntity для удаления
                val taskToDelete = TaskEntity(
                    id = taskId.toInt(),  // Приведение Long к Int
                    title = "",           // Важен только ID для удаления
                    description = null,
                    dueDate = 0L,
                    deadline = 0L
                )
                Log.d("TaskDetailActivity", "TaskEntity created for deletion: $taskToDelete")

                // Вызываем метод удаления в ViewModel
                taskViewModel.deleteTask(taskToDelete)
                Log.d("TaskDetailActivity", "deleteTask method called in ViewModel for ID: ${taskToDelete.id}")

                // Показать сообщение об успешном удалении
                Toast.makeText(this, "Задача удалена", Toast.LENGTH_SHORT).show()

                // Закрываем текущую активность
                finish()
                Log.d("TaskDetailActivity", "Activity finished after task deletion")
            } else {
                // Логируем ошибку, если ID задачи некорректный
                Log.e("TaskDetailActivity", "Invalid Task ID: $taskId")
                Toast.makeText(this, "Ошибка: Невозможно удалить задачу", Toast.LENGTH_SHORT).show()
            }
        }
    }
}