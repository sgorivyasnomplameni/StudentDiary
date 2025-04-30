package com.example.studentdiary.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.R
import com.example.studentdiary.ui.viewmodels.TaskViewModel
import com.example.studentdiary.data.local.entities.TaskEntity
import com.example.studentdiary.ui.activities.AddTaskActivity
import com.example.studentdiary.ui.activities.CalendarActivity
import com.example.studentdiary.ui.activities.TaskDetailActivity
import com.example.studentdiary.ui.adapters.TaskAdapter

class TaskListFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private var isFiltered = false // Переменная для отслеживания состояния фильтрации

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Инициализация RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter(emptyList()) { task, isCompleted ->
            // Обновляем статус выполнения задачи
            taskViewModel.updateTaskCompletion(task.id, isCompleted)
        }
        recyclerView.adapter = taskAdapter

        // Наблюдение за задачами и обновление адаптера
        taskViewModel.getAllTasks().observe(viewLifecycleOwner, { tasks ->
            if (!isFiltered) { // Обновляем только если режим не фильтрации
                taskAdapter.updateTasks(tasks)
            }
        })

        // Кнопка "Добавить задачу"
        val btnAddTask: Button = view.findViewById(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val intent = Intent(requireContext(), AddTaskActivity::class.java)
            startActivity(intent)
        }

        // Кнопка "Фильтровать напоминания"
        val btnFilterReminders: Button = view.findViewById(R.id.btnFilterReminders)
        btnFilterReminders.setOnClickListener {
            isFiltered = !isFiltered // Переключаем состояние фильтрации

            if (isFiltered) {
                // Отображаем только задачи с напоминаниями
                taskViewModel.getTasksWithReminders().observe(viewLifecycleOwner, { tasks ->
                    taskAdapter.updateTasks(tasks)
                })
                btnFilterReminders.text = "Показать все задачи" // Меняем текст кнопки
            } else {
                // Отображаем все задачи
                taskViewModel.getAllTasks().observe(viewLifecycleOwner, { tasks ->
                    taskAdapter.updateTasks(tasks)
                })
                btnFilterReminders.text = "Фильтровать по напоминаниям" // Меняем текст кнопки
            }
        }

        // Кнопка "Открыть календарь"
        val btnOpenCalendar: Button = view.findViewById(R.id.btnOpenCalendar)
        btnOpenCalendar.setOnClickListener {
            val intent = Intent(requireContext(), CalendarActivity::class.java)
            startActivity(intent)
        }

        // Добавляем обработчик кликов для элементов списка
        taskAdapter.setOnItemClickListener { task ->
            // Логируем информацию о задаче
            Log.d("TaskListFragment", "Task clicked: ID = ${task.id}, Title = ${task.title}")

            // Открываем TaskDetailActivity при клике на задачу
            val intent = Intent(requireContext(), TaskDetailActivity::class.java)
            intent.putExtra("TASK_ID", task.id.toLong()) // Передаем ID задачи
            startActivity(intent)
        }

        return view
    }

    private fun showReminderDialog(task: TaskEntity) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Управление напоминанием")

        val input = EditText(requireContext())
        input.hint = "Введите время напоминания (в миллисекундах)"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        input.setText(task.reminderTime?.toString() ?: "")

        dialog.setView(input)

        dialog.setPositiveButton("Обновить") { _, _ ->
            val newReminderTime = input.text.toString().toLongOrNull()
            if (newReminderTime != null) {
                taskViewModel.updateReminder(task.id, newReminderTime)
                Toast.makeText(requireContext(), "Напоминание обновлено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Некорректное время", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setNegativeButton("Удалить") { _, _ ->
            taskViewModel.deleteReminder(task.id)
            Toast.makeText(requireContext(), "Напоминание удалено", Toast.LENGTH_SHORT).show()
        }

        dialog.setNeutralButton("Отмена", null)

        dialog.show()
    }
}