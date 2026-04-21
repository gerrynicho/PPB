package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todolist.ui.components.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(
        listOf(
            Task(title = "Ngoding terus", date = "7 June 2026", isCompleted = false),
            Task(title = "Ngoding", date = "9 June 2026", isCompleted = true),
            Task(title = "Ngoding lagi", date = null, isCompleted = true)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun toggleTask(taskId: String) {
        _tasks.update { currentTasks ->
            currentTasks.map { task ->
                if (task.id == taskId) {
                    task.copy(isCompleted = !task.isCompleted)
                } else {
                    task
                }
            }
        }
    }
}
