package com.example.studentdiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GoalsViewModelFactory(private val goalDao: GoalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalsViewModel(goalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}