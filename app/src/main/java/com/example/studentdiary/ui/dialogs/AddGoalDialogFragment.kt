package com.example.studentdiary.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.studentdiary.R
import com.example.studentdiary.data.local.entities.GoalEntity
import com.google.android.material.textfield.TextInputEditText
import java.util.Date

class AddGoalDialogFragment(
    private val onGoalAdded: (GoalEntity) -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_goal, container, false)

        val nameInput = view.findViewById<TextInputEditText>(R.id.goalNameInput)
        val descriptionInput = view.findViewById<TextInputEditText>(R.id.goalDescriptionInput)
        val addButton = view.findViewById<Button>(R.id.addGoalButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelGoalButton)

        addButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val newGoal = GoalEntity(
                    name = name,
                    description = description,
                    targetProgress = 100,
                    currentProgress = 0,
                    startDate = Date().time,
                    endDate = Date().time + 7 * 24 * 60 * 60 * 1000 // Цель длится 7 дней
                )
                onGoalAdded(newGoal)
                dismiss()
            }
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}