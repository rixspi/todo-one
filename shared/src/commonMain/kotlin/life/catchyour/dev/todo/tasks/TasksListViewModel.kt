package life.catchyour.dev.todo.tasks

import life.catchyour.dev.core.compose.viewmodel.BaseViewModel
import life.catchyour.dev.core.compose.viewmodel.SerializableVMState

class TasksListViewModel(
    private val getTasks: GetTasks,
    private val createTask: CreateTask,
    private val deleteTask: DeleteTask
) : BaseViewModel<TaskListViewState>(
    initialState = TaskListViewState()
) {
    init {
        loadTasks()
    }

    fun loadTasks() {
        setState { copy(isLoading = true) }
        val tasks = getTasks()
        setState { copy(tasks = tasks, isLoading = false) }
    }

    fun createTask(task: TaskItem) {
        createTask(task.toTask())
        loadTasks()
    }

    fun deleteTask(task: TaskItem) {
        deleteTask(task.toTask())
        loadTasks()
    }
}

data class TaskListViewState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false
) : SerializableVMState()