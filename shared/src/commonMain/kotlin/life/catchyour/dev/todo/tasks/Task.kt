package life.catchyour.dev.todo.tasks

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class TaskDb : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    var isCompleted: Boolean = false
}

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)

data class TaskItem(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean
) {
    companion object {
        fun new(title: String, description: String, isCompleted: Boolean) = TaskItem(
            id = "empty",
            title = title,
            description = description,
            isCompleted = isCompleted
        )
    }
}

fun TaskItem.toTask() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun Task.toTaskItem() = TaskItem(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun TaskDb.toTask() = Task(
    id = _id.toString(),
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun Task.toTaskDb() = TaskDb().also {
    it.title = title
    it.description = description
    it.isCompleted = isCompleted
}