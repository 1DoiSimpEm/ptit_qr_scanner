package ptit.vietpq.qr_scanner.presentation.base

import androidx.annotation.MainThread
import java.io.Closeable
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@MainThread
interface HasEventFlow<E> {
  /**
   * Must collect in [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate].
   * Safe to call in the coroutines launched by [androidx.lifecycle.lifecycleScope].
   */
  val eventFlow: Flow<E>
}

@MainThread
interface EventFlowSender<E> {
  /**
   * Must call in [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate].
   * Safe to call in the coroutines launched by [androidx.lifecycle.viewModelScope].
   */
  suspend fun send(event: E)

  fun trySend(event: E)
}

@MainThread
class EventChannel<E> @Inject
constructor() :
  Closeable,
  HasEventFlow<E>,
  EventFlowSender<E> {
  private val _eventChannel = Channel<E>(Channel.UNLIMITED)

  override val eventFlow: Flow<E> by lazy(NONE) {
    _eventChannel.receiveAsFlow()
  }

  init {
    Timber.d("[EventChannel] created: hashCode=${identityHashCode()}")
  }

  /**
   * Must be called in Dispatchers.Main.immediate, otherwise it will throw an exception.
   * If you want to send an event from other Dispatcher,
   * use `withContext(Dispatchers.Main.immediate) { eventChannel.send(event) }`
   */
  @MainThread
  override suspend fun send(event: E) {
    debugCheckImmediateMainDispatcher()

    _eventChannel
      .trySend(event)
      .onClosed { return }
      .onFailure { Timber.e(it, "[EventChannel] Failed to send event: $event, hashCode=${identityHashCode()}") }
      .onSuccess { Timber.d("[EventChannel] Sent event: $event, hashCode=${identityHashCode()}") }
  }

  override fun close() {
    _eventChannel.close()
    Timber.d("[EventChannel] closed: hashCode=${identityHashCode()}")
  }

  override fun trySend(event: E) {
    _eventChannel.trySend(event)
      .onClosed { return }
      .onFailure { Timber.e(it, "[EventChannel] Failed to send event: $event, hashCode=${identityHashCode()}") }
      .onSuccess { Timber.d("[EventChannel] Sent event: $event, hashCode=${identityHashCode()}") }
  }
}

@OptIn(ExperimentalStdlibApi::class)
suspend fun debugCheckImmediateMainDispatcher() {
  if (BuildConfig.DEBUG) {
    val dispatcher = currentCoroutineContext()[CoroutineDispatcher]
    check(dispatcher === Dispatchers.Main.immediate) {
      "Expected CoroutineDispatcher to be Dispatchers.Main.immediate but was $dispatcher"
    }
  }
}

@Suppress("NOTHING_TO_INLINE")
inline fun Any?.identityHashCode(): Int = System.identityHashCode(this)
