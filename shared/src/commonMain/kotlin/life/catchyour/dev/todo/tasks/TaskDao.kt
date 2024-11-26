package life.catchyour.dev.todo.tasks

interface TaskDao {
    fun getTasks(): List<Task>
    fun createTask(task: Task)
    fun deleteTask(task: Task)
}