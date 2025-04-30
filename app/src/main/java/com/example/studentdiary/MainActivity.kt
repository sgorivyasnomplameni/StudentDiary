package com.example.studentdiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Настройка нижней панели навигации с использованием NavController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainer)
        bottomNavigationView.setupWithNavController(navController)

        // Обработка перехода к CalendarActivity
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.calendarFragment) {
                openCalendarActivity() // Открываем CalendarActivity
                true // Возвращаем true, чтобы остановить навигацию NavController
            } else {
                // Позволяем NavController обрабатывать остальные переходы
                navController.navigate(item.itemId)
                true
            }
        }
    }

    /**
     * Открывает активити с календарем
     */
    private fun openCalendarActivity() {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }
}