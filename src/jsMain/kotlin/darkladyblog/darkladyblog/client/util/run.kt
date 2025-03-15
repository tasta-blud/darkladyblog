package darkladyblog.darkladyblog.client.util

import dev.fritz2.core.RootStore
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

fun runLaterTimed(timeout: Int = 1, op: suspend () -> Unit) {
    window.setTimeout({
        MainScope().launch(Dispatchers.Default, start = CoroutineStart.UNDISPATCHED) { op() }
    }, timeout)
}

fun runLater(op: suspend () -> Unit) {
    MainScope().launch(Dispatchers.Default, start = CoroutineStart.UNDISPATCHED) { op() }
}

fun <T> RootStore<T>.runLater(op: suspend () -> T) {
    runWithJob {
        MainScope().launch(Dispatchers.Default, start = CoroutineStart.UNDISPATCHED) {
            flowOf(op()) handledBy update
        }
    }
}