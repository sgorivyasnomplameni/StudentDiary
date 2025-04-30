package com.example.studentdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class SubGoalFragment : Fragment() {

    private lateinit var subGoalName: TextView
    private lateinit var currentProgressText: TextView
    private lateinit var targetProgressText: TextView
    private lateinit var progressInput: EditText
    private lateinit var saveButton: Button

    private val globalGoalViewModel: GlobalGoalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sub_goal, container, false)

        // Инициализация View элементов
        subGoalName = view.findViewById(R.id.subGoalName)
        currentProgressText = view.findViewById(R.id.currentProgressText)
        targetProgressText = view.findViewById(R.id.targetProgressText)
        progressInput = view.findViewById(R.id.progressInput)
        saveButton = view.findViewById(R.id.saveButton)

        // Получение данных подцели через ViewModel
        globalGoalViewModel.currentGlobalGoal.observe(viewLifecycleOwner) { globalGoal ->
            val subGoal = globalGoal?.subGoals?.firstOrNull() // Возьмем первую подцель для примера
            subGoal?.let {
                subGoalName.text = it.name
                currentProgressText.text = "Прогресс: ${it.currentProgress}/${it.targetProgress}"
                targetProgressText.text = "Цель: ${it.targetProgress}"
            }
        }

        // Обработка нажатия кнопки "Сохранить"
        saveButton.setOnClickListener {
            val newProgress = progressInput.text.toString().toIntOrNull() ?: return@setOnClickListener
            globalGoalViewModel.currentGlobalGoal.value?.let { globalGoal ->
                val subGoal = globalGoal.subGoals.firstOrNull() // Возьмем первую подцель для примера
                subGoal?.let {
                    it.currentProgress = newProgress
                    globalGoalViewModel.updateSubGoalProgress(it)
                }
            }
            // Возврат к предыдущему экрану
            findNavController().popBackStack()
        }

        return view
    }
}