package life.catchyour.dev.core.compose.viewmodel

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import life.catchyour.dev.core.db.VMStateRealm


class RealmVMStateStore<T : SerializableVMState>(
    private val savedStateKey: String,
    private val realm: Realm,
    private val json: Json
) : ViewModelStateStorage<T> {

    override fun saveState(state: T) = runBlocking {
        realm.write {
            val existingState =
                realm.query<VMStateRealm>("stateKey == $savedStateKey").find().firstOrNull()
                    ?: VMStateRealm().apply {
                        stateKey = savedStateKey
                        serializedState = json.encodeToString(SerializableVMState.serializer(), state)
                    }

            copyToRealm(
                existingState,
                updatePolicy = UpdatePolicy.ALL
            )
            Unit
        }
    }

    override fun restoreState(): T? {
        return realm.query<VMStateRealm>("stateKey = $savedStateKey").find()[0].let {
            json.decodeFromString(SerializableVMState.serializer(), it.serializedState)
        } as? T
    }

}

// TODO provide stateKey in the constructor, so each view model can define it's key
interface ViewModelStateStorage<State : VMState> {
    fun saveState(state: State)
    fun restoreState(): State?
}