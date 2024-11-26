package life.catchyour.dev.core.compose.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty1

abstract class BaseViewModel<State : VMState>(
    initialState: State,
    private val viewModelStateStorage: ViewModelStateStorage<State>? = null,
) : ScreenModel {
    private val initState = viewModelStateStorage?.restoreState() ?: initialState

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initState)

    protected val viewModeScope = screenModelScope

    val timeCapsule: TimeCapsule<State> = TimeTravelCapsule { storedState ->
        mutableState.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initState)
    }

    val state: StateFlow<State>
        get() = mutableState.asStateFlow()


    fun setState(reduce: State.() -> State) {
        mutableState.update(reduce)
    }

    fun withState(block: (State) -> Unit) {
        block(mutableState.value)
    }

    protected fun <A, B, C> onEach(
        prop1: KProperty1<State, A>,
        prop2: KProperty1<State, B>,
        prop3: KProperty1<State, C>,
        action: suspend (A, B, C) -> Unit
    ) = state
        .map { StateTuple3(prop1.get(it), prop2.get(it), prop3.get(it)) }
        .distinctUntilChanged()
        .resolveSubscription() { (a, b, c) ->
            action(a, b, c)
        }


    @Suppress("EXPERIMENTAL_API_USAGE")
    fun <T : Any> Flow<T>.resolveSubscription(action: suspend (T) -> Unit): Job {
        return viewModeScope.launch(start = CoroutineStart.UNDISPATCHED) {
            // Use yield to ensure flow collect coroutine is dispatched rather than invoked immediately.
            // This is necessary when Dispatchers.Main.immediate is used in scope.
            // Coroutine is launched with start = CoroutineStart.UNDISPATCHED to perform dispatch only once.
            yield()
            collectLatest(action)
        }
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

data class StateTuple1<A>(val a: A)
data class StateTuple2<A, B>(val a: A, val b: B)
data class StateTuple3<A, B, C>(val a: A, val b: B, val c: C)
data class StateTuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
data class StateTuple5<A, B, C, D, E>(val a: A, val b: B, val c: C, val d: D, val e: E)
data class StateTuple6<A, B, C, D, E, F>(
    val a: A,
    val b: B,
    val c: C,
    val d: D,
    val e: E,
    val f: F
)

data class StateTuple7<A, B, C, D, E, F, G>(
    val a: A,
    val b: B,
    val c: C,
    val d: D,
    val e: E,
    val f: F,
    val g: G
)
