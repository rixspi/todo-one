package life.catchyour.dev.core.db

import app.cash.sqldelight.db.SqlDriver
import life.catchyour.dev.Database
import life.catchyour.dev.UserPreferences

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()

    val wrappedBooleanAdapter = WrappedBooleanAdapter()

    val database = Database(
        driver = driver,
        UserPreferencesAdapter = UserPreferences.Adapter(
            useDevEnvAdapter = wrappedBooleanAdapter
        )
    )

    // Do more work with the database (see below).
    return database
}