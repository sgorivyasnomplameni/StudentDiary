package com.example.studentdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText

class UpdateProgressDialogFragment(
    private val goal: GoalEntity,
    private val onProgressUpdated: (GoalEntity) -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_update_progress, container, false)

        val currentProgressInput = view.findViewById<TextInputEditText>(R.id.currentProgressInput)
        val updateButton = view.findViewById<Button>(R.id.updateProgressButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelProgressButton)

        // Устанавливаем текущее значение прогресса
        currentProgressInput.setText(goal.currentProgress.toString())

        // Обработка кнопки обновления
        updateButton.setOnClickListener {
            val updatedCurrentProgress = currentProgressInput.text.toString().toIntOrNull()

            if (updatedCurrentProgress == null) {
                Toast.makeText(requireContext(), "Введите корректное число", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (updatedCurrentProgress > goal.targetProgress) {
                Toast.makeText(requireContext(), "Прогресс не может превышать целевой", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedGoal = goal.copy(currentProgress = updatedCurrentProgress)
            onProgressUpdated(updatedGoal)
            dismiss()
        }

        // Обработка кнопки отмены
        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}