package com.example.studentdiary.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.R
import com.example.studentdiary.data.model.SubGoal

/**
 * Адаптер для отображения списка подцелей в RecyclerView.
 *
 * @param subGoals Список подцелей для отображения.
 * @param onSubGoalClick Обработчик события нажатия на элемент подцели.
 */
class SubGoalAdapter(
    private var subGoals: List<SubGoal>,
    private val onSubGoalClick: (SubGoal) -> Unit
) : RecyclerView.Adapter<SubGoalAdapter.SubGoalViewHolder>() {

    /**
     * ViewHolder представляет элемент списка подцелей.
     *
     * @param itemView Корневой виджет элемента списка.
     */
    class SubGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Название подцели
        val subGoalName: TextView = itemView.findViewById(R.id.subGoalName)

        // Прогресс подцели (визуальный прогресс бар)
        val subGoalProgress: ProgressBar = itemView.findViewById(R.id.subGoalProgress)

        // Прогресс подцели в текстовом формате (например, "30/100")
        val subGoalProgressText: TextView = itemView.findViewById(R.id.subGoalProgressText)
    }

    /**
     * Создаёт ViewHolder для элемента списка.
     *
     * @param parent Родительский ViewGroup, в который будет добавлен элемент списка.
     * @param viewType Тип представления (не используется в данном адаптере, так как все элементы одинаковы).
     * @return Новый экземпляр SubGoalViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubGoalViewHolder {
        // Инфлейт макета item_sub_goal.xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sub_goal, parent, false)
        return SubGoalViewHolder(view)
    }

    /**
     * Привязывает данные подцели к элементу списка.
     *
     * @param holder ViewHolder для текущего элемента.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: SubGoalViewHolder, position: Int) {
        // Получение текущей подцели
        val subGoal = subGoals[position]

        // Установка названия подцели
        holder.subGoalName.text = subGoal.name

        // Установка максимального значения и текущего прогресса в ProgressBar
        holder.subGoalProgress.max = subGoal.targetProgress
        holder.subGoalProgress.progress = subGoal.currentProgress

        // Установка текста прогресса (например, "30/100")
        holder.subGoalProgressText.text = "${subGoal.currentProgress}/${subGoal.targetProgress}"

        // Установка обработчика нажатия на элемент
        holder.itemView.setOnClickListener {
            onSubGoalClick(subGoal) // Вызываем переданный обработчик клика
        }
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return Количество подцелей в списке.
     */
    override fun getItemCount(): Int = subGoals.size

    /**
     * Обновляет список подцелей и уведомляет RecyclerView.
     *
     * @param newSubGoals Новый список подцелей.
     */
    fun updateSubGoals(newSubGoals: List<SubGoal>) {
        subGoals = newSubGoals
        notifyDataSetChanged()
    }
}