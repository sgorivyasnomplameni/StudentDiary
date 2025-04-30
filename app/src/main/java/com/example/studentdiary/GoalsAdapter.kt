package com.example.studentdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalsAdapter(
    private val goals: List<GoalEntity>,
    private val onGoalClick: (GoalEntity) -> Unit // Обработчик нажатий на элемент
) : RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {

    // ViewHolder для хранения ссылок на элементы макета
    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalName: TextView = itemView.findViewById(R.id.goalName)
        val goalDescription: TextView = itemView.findViewById(R.id.goalDescription)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]

        holder.goalName.text = goal.name
        holder.goalDescription.text = goal.description
        holder.progressBar.max = goal.targetProgress
        holder.progressBar.progress = goal.currentProgress

        holder.itemView.setOnClickListener {
            onGoalClick(goal)
        }
    }

    override fun getItemCount(): Int {
        return goals.size
    }
}