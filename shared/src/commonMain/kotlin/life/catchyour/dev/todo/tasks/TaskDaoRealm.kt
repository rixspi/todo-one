package life.catchyour.dev.todo.tasks

import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find
import kotlinx.coroutines.runBlocking

class TaskDaoRealm(
    private val realm: Realm
) : TaskDao {
    override fun getTasks(): List<Task> = realm.query<TaskDb>().find().map { it.toTask() }

    override fun createTask(task: Task) = runBlocking {
        realm.write {
            val taskDb = task.toTaskDb()

            copyToRealm(taskDb)
            Unit
        }
    }

    override fun deleteTask(task: Task) = runBlocking {
        realm.write {
            val taskDb = realm.query<TaskDb>("id==$0", task.id).find().first()
            delete(taskDb)
        }
    }
}