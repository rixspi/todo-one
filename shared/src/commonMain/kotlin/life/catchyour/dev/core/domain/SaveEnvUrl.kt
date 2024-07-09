package life.catchyour.dev.core.domain

import life.catchyour.dev.DatabaseQueries
import life.catchyour.dev.core.db.wrap


class SaveEnvUrl(
    private val prefs: DatabaseQueries
) {
    operator fun invoke(useDev: Boolean) {
        prefs.useDevEnv(useDev.wrap())
    }
}
