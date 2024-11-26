package life.catchyour.dev.todo.tasks

class CreateTask(
    private val taskDao: TaskDao
) {
    operator fun invoke(task: Task) = taskDao.createTask(task)
}