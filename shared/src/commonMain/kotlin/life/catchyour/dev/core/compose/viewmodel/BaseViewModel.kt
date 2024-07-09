package life.catchyour.dev.core.compose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

abstract class BaseViewModel<State : VMState>(
    initialState: State,
    private val viewModelStateStorage: ViewModelStateStorage<State>? = null,
) : ViewModel() {
    private val initState = viewModelStateStorage?.restoreState() ?: initialState

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initState)

    val timeCapsule: TimeCapsule<State> = TimeTravelCapsule { storedState ->
        mutableState.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initState)
    }

    val state: StateFlow<State>
        get() = mutableState.asStateFlow()

    fun setState(reduce: (State) -> State) {
        mutableState.update(reduce)
    }

    fun withState(block: State.() -> Unit) {
        block(mutableState.value)
    }

    private inline fun MutableStateFlow<State>.update(function: (State) -> State) {
        while (true) {
            val prevValue = value
            val nextValue = function(prevValue)
            if (compareAndSet(prevValue, nextValue)) {
                viewModelStateStorage?.saveState(nextValue)
                timeCapsule.addState(nextValue)
                return
            }
        }
    }
}

interface VMState

@Serializable
/**
 * In order for state to be serializable, it has to provide a serializer to the Json serializer used by this code.
 * This is done by providing a polymorphic serializer for the state class.
 * It has to be available at the time of injection to be provided via constructor.
 *
 * Example:
 *     private val json = Json {
 *         serializersModule = SerializersModule {
 *             polymorphic(SerializableVMState::class) {
 *
 *             }
 *         }
 *     }
 */
abstract class SerializableVMState : VMState
