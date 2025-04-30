package com.example.studentdiary.ui.decorators

import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import java.util.*
import android.view.ViewGroup

class EventDecorator(
    private val color: Int,
    private val dates: Set<Long> // Храним даты как timestamp
) {
    fun decorate(calendarView: CalendarView) {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val timestamp = calendar.timeInMillis

            if (dates.contains(timestamp)) {
                // Находим TextView внутри CalendarView и меняем цвет
                val textView = findTextViewInCalendarView(calendarView)
                textView?.setTextColor(color)
            }
        }
    }

    private fun findTextViewInCalendarView(view: View): TextView? {
        if (view is TextView) {
            return view
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = findTextViewInCalendarView(view.getChildAt(i))
                if (child != null) return child
            }
        }
        return null
    }
}