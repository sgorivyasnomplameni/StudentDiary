package com.example.studentdiary.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.studentdiary.R
import com.example.studentdiary.data.local.entities.GoalEntity
import com.google.android.material.textfield.TextInputEditText

class EditGoalDialogFragment(
    private val goal: GoalEntity,
    private val onGoalUpdated: (GoalEntity, String) -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_edit_goal, container, false)

        // Инициализация UI элементов
        val nameInput = view.requireViewById<TextInputEditText>(R.id.goalNameInput)
        val descriptionInput = view.requireViewById<TextInputEditText>(R.id.goalDescriptionInput)
        val currentProgressInput = view.requireViewById<TextInputEditText>(R.id.goalCurrentProgressInput)
        val targetProgressInput = view.requireViewById<TextInputEditText>(R.id.goalTargetProgressInput)
        val updateButton = view.requireViewById<Button>(R.id.updateGoalButton)
        val deleteButton = view.requireViewById<Button>(R.id.deleteGoalButton)
        val cancelButton = view.requireViewById<Button>(R.id.cancelGoalButton)

        // Заполняем поля текущими данными цели
        populateFields(nameInput, descriptionInput, currentProgressInput, targetProgressInput)

        // Устанавливаем обработчики кликов
        setupButtonListeners(
            nameInput,
            descriptionInput,
            currentProgressInput,
            targetProgressInput,
            updateButton,
            deleteButton,
            cancelButton
        )

        return view
    }

    private fun populateFields(
        nameInput: TextInputEditText,
        descriptionInput: TextInputEditText,
        currentProgressInput: TextInputEditText,
        targetProgressInput: TextInputEditText
    ) {
        nameInput.setText(goal.name)
        descriptionInput.setText(goal.description)
        currentProgressInput.setText(goal.currentProgress.toString())
        targetProgressInput.setText(goal.targetProgress.toString())
    }

    private fun setupButtonListeners(
        nameInput: TextInputEditText,
        descriptionInput: TextInputEditText,
        currentProgressInput: TextInputEditText,
        targetProgressInput: TextInputEditText,
        updateButton: Button,
        deleteButton: Button,
        cancelButton: Button
    ) {
        updateButton.setOnClickListener {
            handleUpdate(nameInput, descriptionInput, currentProgressInput, targetProgressInput)
        }

        deleteButton.setOnClickListener {
            onGoalUpdated(goal, "delete")
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun handleUpdate(
        nameInput: TextInputEditText,
        descriptionInput: TextInputEditText,
        currentProgressInput: TextInputEditText,
        targetProgressInput: TextInputEditText
    ) {
        val updatedName = nameInput.text?.toString()?.trim().orEmpty()
        val updatedDescription = descriptionInput.text?.toString()?.trim().orEmpty()
        val updatedCurrentProgress = currentProgressInput.text?.toString()?.toIntOrNull()
        val updatedTargetProgress = targetProgressInput.text?.toString()?.toIntOrNull()

        when {
            updatedName.isEmpty() || updatedDescription.isEmpty() -> {
                showToast(getString(R.string.error_fields_empty))
                return
            }
            updatedCurrentProgress == null || updatedTargetProgress == null -> {
                showToast(getString(R.string.error_progress_not_number))
                return
            }
            updatedTargetProgress <= 0 -> {
                showToast(getString(R.string.error_target_progress_positive))
                return
            }
            updatedCurrentProgress > updatedTargetProgress -> {
                showToast(getString(R.string.error_current_exceeds_target))
                return
            }
            else -> {
                val updatedGoal = goal.copy(
                    name = updatedName,
                    description = updatedDescription,
                    currentProgress = updatedCurrentProgress,
                    targetProgress = updatedTargetProgress
                )
                onGoalUpdated(updatedGoal, "update")
                dismiss()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}