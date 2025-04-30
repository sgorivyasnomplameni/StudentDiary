package com.example.studentdiary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.data.model.GlobalGoal
import com.example.studentdiary.ui.viewmodels.GoalsViewModelFactory
import com.example.studentdiary.R
import com.example.studentdiary.StudentDiaryApp
import com.example.studentdiary.data.local.mappers.toGlobalGoal
import com.example.studentdiary.ui.adapters.GoalsAdapter
import com.example.studentdiary.ui.viewmodels.GlobalGoalViewModel
import com.example.studentdiary.ui.viewmodels.GoalsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GoalsListFragment : Fragment() {

    private val goalsViewModel: GoalsViewModel by viewModels {
        GoalsViewModelFactory((requireActivity().application as StudentDiaryApp).database.goalDao())
    }
    private val globalGoalViewModel: GlobalGoalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.goalsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            goalsViewModel.allGoals.collectLatest { goalEntities ->
                // Преобразуем GoalEntity в GlobalGoal
                val globalGoals = goalEntities.map { it.toGlobalGoal() }
                recyclerView.adapter = GoalsAdapter(globalGoals) { globalGoal ->
                    onGoalClick(globalGoal)
                }
            }
        }

        return view
    }

    private fun onGoalClick(goal: GlobalGoal) {
        globalGoalViewModel.setCurrentGlobalGoal(goal.id)
        val action = GoalsListFragmentDirections.actionGoalsListFragmentToGlobalGoalFragment()
        findNavController().navigate(action)
    }
}