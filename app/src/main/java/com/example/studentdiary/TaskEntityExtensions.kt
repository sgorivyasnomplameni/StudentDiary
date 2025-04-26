package com.example.studentdiary

import java.text.SimpleDateFormat
import java.util.*

fun TaskEntity.getFormattedDate(): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return dateFormat.format(this.dueDate)
}