package ptit.vietpq.qr_scanner.extension

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**

 * The returned [Flow] is cancellable and has the same behaviour as [kotlinx.coroutines.flow.cancellable].
 *
 * ## Example of usage:
 *
 * ```kotlin
 * suspend fun remoteCall(): R = ...
 * fun remoteCallFlow(): Flow<R> = flowFromSuspend(::remoteCall)
 * ```
 *
 * ## Another example:
 *
 * ```kotlin
 * var count = 0L
 * val flow = flowFromSuspend {
 *   delay(count)
 *   count++
 * }
 *
 * flow.collect { println("flowFromSuspend: $it") }
 * println("---")
 * flow.collect { println("flowFromSuspend: $it") }
 * println("---")
 * flow.collect { println("flowFromSuspend: $it") }
 * ```
 *
 * Output:
 *
 * ```none
 * flowFromSuspend: 0
 * ---
 * flowFromSuspend: 1
 * ---
 * flowFromSuspend: 2
 * ```
 *
 * @see flowFromNonSuspend
 */

fun <T> flowFromSuspend(function: suspend () -> T): Flow<T> = FlowFromSuspend(function)

// We don't need to use `AbstractFlow` here because we only emit a single value without a context switch,
// and we guarantee all Flow's constraints: context preservation and exception transparency.
private class FlowFromSuspend<T>(private val function: suspend () -> T) : Flow<T> {
  override suspend fun collect(collector: FlowCollector<T>) {
    val value = function()
    currentCoroutineContext().ensureActive()
    collector.emit(value)
  }
}
