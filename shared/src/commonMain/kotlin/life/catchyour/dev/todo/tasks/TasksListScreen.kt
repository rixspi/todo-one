package life.catchyour.dev.todo.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import life.catchyour.dev.core.compose.listComponenets.CheckboxText
import life.catchyour.dev.core.compose.listComponenets.ItemTextRow
import kotlin.reflect.KFunction1

class TasksListScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TasksListViewModel>()

        val state = screenModel.state.collectAsState()

        Scaffold {
            Column {
                TaskForm(
                    onTaskCreate = screenModel::createTask
                )
                Text("Tasks")
                TasksList(
                    tasks = state.value.tasks,
                    isLoading = state.value.isLoading,
                    onTaskCreate = screenModel::createTask,
                    onTaskDelete = screenModel::deleteTask
                )
            }
        }
    }

    @Composable
    fun TaskForm(onTaskCreate: (TaskItem) -> Unit) {
        Column {
            Text("Create Task")

            var taskTitle by remember { mutableStateOf("") }
            ItemTextRow()
            TextField(
                value = taskTitle,
                onValueChange = {
                    taskTitle = it
                },
                label = { Text("Title") }
            )

            var taskDescription by remember { mutableStateOf("") }
            TextField(
                value = taskDescription,
                onValueChange = {
                    taskDescription = it
                },
                label = { Text("Description") }
            )

            var isCompleted by remember { mutableStateOf(false) }
            CheckboxText(
                text = "Is Completed",
                isSelected = isCompleted,
                onSelectedChange = {
                    isCompleted = it
                }
            )

            TextButton(
                onClick = {
                    onTaskCreate(
                        TaskItem.new(
                            title = taskTitle,
                            description = taskDescription,
                            isCompleted = isCompleted
                        )
                    )
                }
            ) {
                Text("Create Task")
            }
        }
    }

    @Composable
    fun TasksList(
        modifier: Modifier = Modifier,
        tasks: List<Task>,
        isLoading: Boolean,
        onTaskCreate: (TaskItem) -> Unit,
        onTaskDelete: (TaskItem) -> Unit
    ) {
        LazyColumn {
            items(tasks.size) { taskIndex ->
                val task = tasks[taskIndex]

                Column {
                    Text(task.title)
                    Text(task.description)
                }
            }
        }
    }
}