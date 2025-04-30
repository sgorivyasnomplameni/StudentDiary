package com.example.studentdiary.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.data.model.GlobalGoal
import com.example.studentdiary.ui.viewmodels.GlobalGoalViewModel
import com.example.studentdiary.R
import com.example.studentdiary.data.model.SubGoal
import com.example.studentdiary.ui.adapters.SubGoalAdapter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class GlobalGoalFragment : Fragment() {

    private lateinit var pieChart: PieChart
    private lateinit var recyclerView: RecyclerView
    private val globalGoalViewModel: GlobalGoalViewModel by viewModels()
    private lateinit var subGoalAdapter: SubGoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_global_goal, container, false)

        // Инициализация элементов
        pieChart = view.findViewById(R.id.pieChart)
        recyclerView = view.findViewById(R.id.subGoalsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Инициализация адаптера
        subGoalAdapter = SubGoalAdapter(emptyList()) { subGoal ->
            // Передаем subGoal в метод onSubGoalClick
            onSubGoalClick(subGoal)
        }
        recyclerView.adapter = subGoalAdapter

        // Наблюдение за выбранной глобальной целью
        globalGoalViewModel.currentGlobalGoal.observe(viewLifecycleOwner) { globalGoal ->
            if (globalGoal != null) {
                updatePieChart(globalGoal)
                subGoalAdapter.updateSubGoals(globalGoal.subGoals)
            }
        }

        return view
    }

    private fun updatePieChart(globalGoal: GlobalGoal) {
        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(globalGoal.currentProgress.toFloat(), "Выполнено"))
        entries.add(
            PieEntry(
                (globalGoal.targetProgress - globalGoal.currentProgress).toFloat(),
                "Осталось"
            )
        )

        val dataSet = PieDataSet(entries, "Прогресс")
        dataSet.colors = listOf(Color.GREEN, Color.LTGRAY)
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 14f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate() // Обновление диаграммы
    }

    private fun onSubGoalClick(subGoal: SubGoal) {
        // Переход на SubGoalFragment может быть обновлен, если требуется передать данные
        findNavController().navigate(R.id.action_globalGoalFragment_to_subGoalFragment)
    }
}