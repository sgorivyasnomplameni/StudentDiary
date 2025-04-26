package com.example.studentdiary

import java.text.SimpleDateFormat
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    val dueDate: Long,  // timestamp
    val deadline: Long  // timestamp
) {

    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(dueDate)
        return sdf.format(date)
    }
}
