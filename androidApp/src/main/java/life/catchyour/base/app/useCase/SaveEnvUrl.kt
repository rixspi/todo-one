package life.catchyour.base.app.useCase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import life.catchyour.base.app.useCase.GetEnvUrl.Companion.ENV_KEY
import javax.inject.Inject

class SaveEnvUrl @Inject constructor(
    private val prefs: DataStore<Preferences>
) {
    suspend operator fun invoke(useDev: Boolean) {
        prefs.edit { settings ->
            settings[ENV_KEY] = useDev
        }
    }
}
