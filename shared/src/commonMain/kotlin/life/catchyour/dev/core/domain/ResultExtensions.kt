package life.catchyour.dev.core.domain

import kotlin.Result.Companion.failure
import kotlin.coroutines.cancellation.CancellationException

/**
 * Default implementation of [Result.runCatching] catches [Error] which is a fatal problem
 * the app can't recover from.
 *
 * Same goes for [CancellationException] which is thrown when a coroutine is cancelled.
 * The exception is used to cancel the coroutine and all its children, so it shouldn't be caught
 * in general, just in specific cases.
 */
inline fun <T> Result.Companion.catch(f: () -> T): Result<T> {
    return try {
        success(f())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Error) {
        throw e
    } catch (e: Throwable) {
        failure(e)
    }
}

inline fun <A, B> Result<A>.flatMap(f: (A) -> Result<B>): Result<B> = when (isFailure) {
    true -> failure(exceptionOrNull()!!)
    false -> f(getOrNull()!!)
}