package life.catchyour.dev.todo.tasks

class DeleteTask(
    private val taskDao: TaskDao
) {
    operator fun invoke(task: Task) {
        taskDao.deleteTask(task)
    }
}
