package com.example.studentdiary

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class GlobalGoalFragment : Fragment() {

    private lateinit var pieChart: PieChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var globalGoalViewModel: GlobalGoalViewModel
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

        // Инициализация ViewModel
        globalGoalViewModel = ViewModelProvider(this).get(GlobalGoalViewModel::class.java)

        // Инициализация адаптера
        subGoalAdapter = SubGoalAdapter(emptyList()) { subGoal ->
            // Обработка нажатий на подцели
            globalGoalViewModel.updateSubGoalProgress(subGoal)
        }
        recyclerView.adapter = subGoalAdapter

        // Наблюдение за данными глобальной цели
        globalGoalViewModel.globalGoal.observe(viewLifecycleOwner) { globalGoal ->
            updatePieChart(globalGoal)
            subGoalAdapter = SubGoalAdapter(globalGoal.subGoals) { subGoal ->
                globalGoalViewModel.updateSubGoalProgress(subGoal)
            }
            recyclerView.adapter = subGoalAdapter
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
}