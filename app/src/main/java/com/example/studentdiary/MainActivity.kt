package com.example.studentdiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_CALENDAR = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Устанавливаем начальный фрагмент и синхронизируем его с нижней навигацией
        if (savedInstanceState == null) {
            setInitialFragment()
        }

        // Настройка нижней панели навигации
        setupBottomNavigation()
    }

    /**
     * Устанавливает начальный фрагмент и синхронизирует его с выбранным пунктом навигации
     */
    private fun setInitialFragment() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.menu_goals // Устанавливаем "Цели" как выбранный пункт
        loadFragment(GlobalGoalFragment()) // Загружаем фрагмент "Глобальная цель"
    }

    /**
     * Настраивает обработчик выбора пунктов меню в нижней навигации
     */
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_tasks -> {
                    loadFragment(TaskListFragment()) // Переход на экран "Задачи"
                    true
                }
                R.id.menu_goals -> {
                    loadFragment(GlobalGoalFragment()) // Переход на экран "Глобальная цель"
                    true
                }
                R.id.menu_calendar -> {
                    openCalendarActivity() // Открытие активности "Календарь"
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Загружает фрагмент в контейнер
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    /**
     * Открывает активити с календарем
     */
    private fun openCalendarActivity() {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CALENDAR)
    }

    /**
     * Обрабатывает результат возврата из CalendarActivity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CALENDAR && resultCode == RESULT_OK) {
            val selectedMenuItem = data?.getIntExtra("selectedMenuItem", R.id.menu_goals) ?: R.id.menu_goals
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.selectedItemId = selectedMenuItem

            // Синхронизируем выбранный пункт с фрагментом
            when (selectedMenuItem) {
                R.id.menu_tasks -> loadFragment(TaskListFragment())
                R.id.menu_goals -> loadFragment(GlobalGoalFragment())
            }
        }
    }
}