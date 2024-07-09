package life.catchyour.dev.core.domain

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import life.catchyour.dev.DatabaseQueries
import life.catchyour.dev.core.db.unwrap

class GetEnvUrl(
    private val databaseQueries: DatabaseQueries
) {
    operator fun invoke(): Flow<Boolean> =
        databaseQueries.defaultPreferences().asFlow().mapToOneOrNull(Dispatchers.Default).map {
            it?.useDevEnv?.unwrap() ?: false
        }
}
