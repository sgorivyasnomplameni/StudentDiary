package com.example.studentdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Устанавливаем начальный фрагмент, если приложение только запущено
        if (savedInstanceState == null) {
            loadFragment(GoalsListFragment()) // Начинаем с экрана "Цели"
        }

        // Настройка нижней навигации
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_tasks -> {
                    loadFragment(TaskListFragment()) // Переключение на экран "Задачи"
                    true
                }
                R.id.menu_goals -> {
                    loadFragment(GoalsListFragment()) // Переключение на экран "Цели"
                    true
                }
                else -> false
            }
        }
    }

    // Метод для загрузки фрагмента в контейнер
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}