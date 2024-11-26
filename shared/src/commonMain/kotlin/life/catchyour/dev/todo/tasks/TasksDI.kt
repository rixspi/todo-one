package life.catchyour.dev.todo.tasks

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.mongodb.kbson.BsonObjectId

object TasksDI {

    // Powinienem przekazywać realm do TaskDao w zależnosći, czy to UI czy background
    //  w background muszę otworzyć nową instancję realm, zrobić co trzeba i zamknąć

    val taskModule = module {

        single<Realm>(named(REALM_DEFAULT)) {
            val configuration = RealmConfiguration.Builder(
                schema = setOf(TaskDb::class),
            )
                .initialData {
                    copyToRealm(TaskDb().apply {
                        _id = org.mongodb.kbson.ObjectId()
                        title = "Task 1"
                        description = "Description 1"
                        isCompleted = false
                    })
                }
                .build()

            Realm.open(configuration)
        }

        single<TaskDao> { TaskDaoRealm(realm = get(named(REALM_DEFAULT))) }

        factory { GetTasks(taskDao = get()) }
        factory { CreateTask(taskDao = get()) }
        factory { DeleteTask(taskDao = get()) }

        factory {
            TasksListViewModel(
                getTasks = get(),
                createTask = get(),
                deleteTask = get()
            )
        }
    }
}


const val REALM_DEFAULT = "DefaultRealm"
const val REALM_BACKGROUND = "BackgroundRealm"