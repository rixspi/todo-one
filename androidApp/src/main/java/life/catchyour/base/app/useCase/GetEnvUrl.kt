package life.catchyour.base.app.useCase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEnvUrl @Inject constructor(
    private val prefs: DataStore<Preferences>
) {
    operator fun invoke(): Flow<Boolean> =
        prefs.data.map { settings ->
            settings[ENV_KEY]
        }.filterNotNull()

    companion object {
        val ENV_KEY = booleanPreferencesKey("env_key")
    }
}
