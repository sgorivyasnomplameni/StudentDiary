package com.example.studentdiary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.CalendarView
import java.util.*
import kotlin.math.roundToInt

class CalendarDecorator(
    private val context: Context,
    private val calendarView: CalendarView,
    private val datesWithTasks: List<Long>
) : View(context) {
    private val paint = Paint().apply {
        color = context.getColor(android.R.color.holo_blue_light) // Цвет индикатора
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val radius = 8f.dpToPx()

    init {
        calendarView.setOnDateChangeListener { _, _, _, _ ->
            invalidate() // Перерисовать индикаторы при изменении даты
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawIndicators(canvas)
    }

    private fun drawIndicators(canvas: Canvas) {
        val currentMonth = Calendar.getInstance().apply {
            timeInMillis = calendarView.date
        }.get(Calendar.MONTH)

        val currentYear = Calendar.getInstance().apply {
            timeInMillis = calendarView.date
        }.get(Calendar.YEAR)

        datesWithTasks.forEach { date ->
            val taskDate = Calendar.getInstance().apply { timeInMillis = date }
            if (taskDate.get(Calendar.MONTH) == currentMonth && taskDate.get(Calendar.YEAR) == currentYear) {
                val x = calculateX(taskDate)
                val y = calculateY(taskDate)
                canvas.drawCircle(x, y, radius.toFloat(), paint) // Рисуем индикатор
            }
        }
    }

    private fun calculateX(taskDate: Calendar): Float {
        val dayOfWeek = taskDate.get(Calendar.DAY_OF_WEEK) // День недели (1 = воскресенье, 7 = суббота)
        val cellWidth = calendarView.width / 7 // Ширина одной ячейки (7 дней в неделе)
        return ((dayOfWeek - 1) * cellWidth + cellWidth / 2).toFloat() // Центр ячейки
    }

    private fun calculateY(taskDate: Calendar): Float {
        val dayOfMonth = taskDate.get(Calendar.DAY_OF_MONTH)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = calendarView.date
        }

        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) // День недели первого числа месяца
        val row = ((dayOfMonth + firstDayOfMonth - 2) / 7) // Номер строки (0 = первая неделя)
        val cellHeight = calendarView.height / 6 // Высота одной ячейки (максимум 6 строк)
        return (row * cellHeight + cellHeight / 2).toFloat() // Центр ячейки
    }

    private fun Float.dpToPx(): Int {
        return (this * context.resources.displayMetrics.density).roundToInt()
    }
}