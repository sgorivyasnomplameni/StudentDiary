package com.example.studentdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GoalsListFragment : Fragment() {

    // Инициализация ViewModel с использованием фабрики
    private val goalsViewModel: GoalsViewModel by viewModels {
        GoalsViewModelFactory((requireActivity().application as StudentDiaryApp).database.goalDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals_list, container, false)

        // Настройка RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.goalsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // FloatingActionButton для добавления новой цели
        val addGoalFab = view.findViewById<FloatingActionButton>(R.id.addGoalFab)
        addGoalFab.setOnClickListener {
            showAddGoalDialog()
        }

        // Наблюдение за обновлением списка целей
        lifecycleScope.launch {
            goalsViewModel.allGoals.collectLatest { goals ->
                recyclerView.adapter = GoalsAdapter(goals) { goal ->
                    onGoalClick(goal)
                }
            }
        }

        return view
    }

    // Обработка нажатий на элементы списка
    private fun onGoalClick(goal: GoalEntity) {
        showEditGoalDialog(goal)
    }

    // Открытие диалога для добавления новой цели
    private fun showAddGoalDialog() {
        val dialog = AddGoalDialogFragment { newGoal ->
            goalsViewModel.addGoal(newGoal)
        }
        dialog.show(parentFragmentManager, "AddGoalDialog")
    }

    // Открытие диалога для редактирования выбранной цели
    private fun showEditGoalDialog(goal: GoalEntity) {
        val dialog = EditGoalDialogFragment(goal) { updatedGoal, action ->
            when (action) {
                "update" -> {
                    goalsViewModel.updateGoal(updatedGoal)
                }
                "delete" -> {
                    goalsViewModel.deleteGoal(goal)
                }
                "progress" -> {
                    showUpdateProgressDialog(updatedGoal)
                }
            }
        }
        dialog.show(parentFragmentManager, "EditGoalDialog")
    }

    // Открытие диалога для обновления прогресса цели
    private fun showUpdateProgressDialog(goal: GoalEntity) {
        val dialog = UpdateProgressDialogFragment(goal) { updatedGoal ->
            goalsViewModel.updateGoal(updatedGoal)
        }
        dialog.show(parentFragmentManager, "UpdateProgressDialog")
    }
}