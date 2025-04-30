package com.example.studentdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
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

        val nameInput = view.findViewById<TextInputEditText>(R.id.goalNameInput)
        val descriptionInput = view.findViewById<TextInputEditText>(R.id.goalDescriptionInput)
        val updateButton = view.findViewById<Button>(R.id.updateGoalButton)
        val deleteButton = view.findViewById<Button>(R.id.deleteGoalButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelGoalButton)

        // Заполняем поля текущими данными цели
        nameInput.setText(goal.name)
        descriptionInput.setText(goal.description)

        updateButton.setOnClickListener {
            val updatedName = nameInput.text.toString().trim()
            val updatedDescription = descriptionInput.text.toString().trim()

            if (updatedName.isNotEmpty() && updatedDescription.isNotEmpty()) {
                val updatedGoal = goal.copy(
                    name = updatedName,
                    description = updatedDescription
                )
                onGoalUpdated(updatedGoal, "update")
                dismiss()
            }
        }

        deleteButton.setOnClickListener {
            onGoalUpdated(goal, "delete")
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}