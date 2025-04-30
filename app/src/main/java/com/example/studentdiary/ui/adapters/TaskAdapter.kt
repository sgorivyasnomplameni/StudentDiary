package com.example.studentdiary.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.CheckBox
import android.widget.ImageView
import com.example.studentdiary.R
import com.example.studentdiary.data.local.entities.TaskEntity

class TaskAdapter(
    private var tasks: List<TaskEntity>,
    private val onTaskCompletionChanged: (TaskEntity, Boolean) -> Unit // Новый callback для изменения статуса выполнения
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var onItemClickListener: ((TaskEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    fun updateTasks(newTasks: List<TaskEntity>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)

        // Обработка клика на весь элемент
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(task)
        }

        // Обработка изменения состояния CheckBox
        holder.taskCompletedCheckbox.setOnCheckedChangeListener(null) // Убираем ранее установленный listener
        holder.taskCompletedCheckbox.isChecked = task.completed
        holder.taskCompletedCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onTaskCompletionChanged(task, isChecked) // Вызываем callback при изменении состояния CheckBox
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun setOnItemClickListener(listener: (TaskEntity) -> Unit) {
        onItemClickListener = listener
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskDueDate: TextView = itemView.findViewById(R.id.taskDueDate)
        val reminderIndicator: ImageView = itemView.findViewById(R.id.reminderIndicator)
        val taskCompletedCheckbox: CheckBox = itemView.findViewById(R.id.checkboxCompleted) // Новый элемент

        fun bind(task: TaskEntity) {
            taskTitle.text = task.title
            taskDescription.text = task.description ?: ""
            taskDueDate.text = task.getFormattedDate()

            // Отображаем индикатор напоминания, если reminderTime установлен
            if (task.reminderTime != null) {
                reminderIndicator.visibility = View.VISIBLE
            } else {
                reminderIndicator.visibility = View.GONE
            }
        }
    }
}