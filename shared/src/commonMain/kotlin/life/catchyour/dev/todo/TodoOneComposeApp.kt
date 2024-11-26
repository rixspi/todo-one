package life.catchyour.dev.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import io.realm.kotlin.Realm
import life.catchyour.dev.core.logging.KoinLogger
import life.catchyour.dev.todo.tasks.REALM_DEFAULT
import life.catchyour.dev.todo.tasks.TasksDI
import life.catchyour.dev.todo.tasks.TasksListScreen
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import kotlin.math.log


@Composable
fun TodoOneComposeApp() {
    KoinApplication(
        application = {
            logger(KoinLogger())
            modules(TasksDI.taskModule)
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(TasksListScreen())
        }

        val realm = koinInject<Realm>(named(REALM_DEFAULT))
        DisposableEffect(Unit) {
            onDispose { realm.close() }
        }
    }
}
