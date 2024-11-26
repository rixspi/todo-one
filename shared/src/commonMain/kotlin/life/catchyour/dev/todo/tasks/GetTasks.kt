package life.catchyour.dev.todo.tasks

class GetTasks(
    private val taskDao: TaskDao
) {
    operator fun invoke(): List<Task> =
        taskDao.getTasks()
}