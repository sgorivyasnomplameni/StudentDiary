package com.example.studentdiary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.CalendarView
import java.util.*
import kotlin.math.roundToInt

class CalendarDecorator(
    private val context: Context,
    private val calendarView: CalendarView,
    private val datesWithTasks: List<Long>
) {
    private val paint = Paint().apply {
        color = context.getColor(android.R.color.holo_blue_light)
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val radius = 8f.dpToPx()

    fun decorate() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendarView.invalidate()
        }
    }

    private fun Float.dpToPx(): Int {
        return (this * context.resources.displayMetrics.density).roundToInt()
    }

    companion object {
        fun drawIndicators(
            canvas: Canvas,
            paint: Paint,
            radius: Int,
            dates: List<Long>,
            calendarView: CalendarView
        ) {
            try {
                val currentDate = Calendar.getInstance().apply {
                    timeInMillis = calendarView.date
                }
                val month = currentDate.get(Calendar.MONTH)
                val year = currentDate.get(Calendar.YEAR)

                dates.forEach { date ->
                    val cal = Calendar.getInstance().apply { timeInMillis = date }
                    if (cal.get(Calendar.MONTH) == month && cal.get(Calendar.YEAR) == year) {
                        // Логика рисования будет добавлена позже
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}