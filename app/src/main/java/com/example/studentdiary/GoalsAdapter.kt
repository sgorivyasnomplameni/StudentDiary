package com.example.studentdiary

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GoalsAdapter(
    private val goals: List<GlobalGoal>, // Изменено на GlobalGoal
    private val onGoalClick: (GlobalGoal) -> Unit // Обработчик нажатий на элемент
) : RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {

    // ViewHolder для хранения ссылок на элементы макета
    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalName: TextView = itemView.findViewById(R.id.goalName)
        val goalDescription: TextView = itemView.findViewById(R.id.goalDescription)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val progressText: TextView = itemView.findViewById(R.id.progressText) // Текст для отображения прогресса
        val completedIcon: ImageView = itemView.findViewById(R.id.completedIcon) // Иконка завершения
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]

        holder.goalName.text = goal.name
        holder.goalDescription.text = "Описание отсутствует" // GlobalGoal не имеет description
        holder.progressBar.max = goal.targetProgress
        holder.progressBar.progress = goal.currentProgress
        holder.progressText.text = "${goal.currentProgress}/${goal.targetProgress}"

        // Проверяем, завершена ли цель
        if (goal.currentProgress >= goal.targetProgress) {
            holder.completedIcon.visibility = View.VISIBLE
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.completed_goal_background))
        } else {
            holder.completedIcon.visibility = View.GONE
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.itemView.setOnClickListener {
            onGoalClick(goal) // Передаём GlobalGoal
        }
    }

    override fun getItemCount(): Int {
        return goals.size
    }
}