package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.components.HeaderCard
import com.example.todolist.ui.components.SectionHeader
import com.example.todolist.ui.components.Task
import com.example.todolist.ui.components.TaskItem
import com.example.todolist.ui.theme.LightBlue
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                         floatingActionButton = { FloatingActionButton(
                             onClick = {},
                             containerColor = LightBlue,
                             contentColor = Color.White,
                             shape = CircleShape
                         ) {
                             Icon(Icons.Filled.Add, contentDescription = "Add Task")
                         } }
                    ) { innerPadding ->
                    val tasks by taskViewModel.tasks.collectAsState()
                    App(
                        tasks = tasks,
                        onToggleTask = { taskViewModel.toggleTask(it) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ToDoListTheme {
        val dummyTasks = listOf<Task>(
            Task(title = "Ngoding terus", date = "7 June 2026", isCompleted = false),
            Task(title = "Ngoding", date = "9 June 2026", isCompleted = true),
            Task(title = "Ngoding lagi", date = null,  isCompleted = true)
        )
        Scaffold(modifier = Modifier.fillMaxSize(),
            floatingActionButton = { FloatingActionButton(
                onClick = {},
                containerColor = LightBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            } }
        ) { innerPadding ->
            App(
                tasks = dummyTasks,
                onToggleTask = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun App(tasks: List<Task>, onToggleTask: (String) -> Unit, modifier: Modifier = Modifier) {
    val pendingTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            HeaderCard()
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title="Task", actionText = "See all")
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(items = pendingTasks) { task ->
            TaskItem(
                title = task.title, 
                date = task.date, 
                isCompleted = task.isCompleted,
                onCheckedChange = { onToggleTask(task.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title="Completed", icon = Icons.Filled.KeyboardArrowDown)
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(items = completedTasks) { task ->
            TaskItem(
                title = task.title, 
                date = task.date, 
                isCompleted = task.isCompleted,
                onCheckedChange = { onToggleTask(task.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // Added space for floating action button
        }
    }
}