package ghanshyam.demo.anzusersapp.core.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

interface ViewIntent
interface ViewState
interface ViewEffect


abstract class MviViewModel<I : ViewIntent, S : ViewState, E : ViewEffect>(
    contextProvider: CoroutineContextProvider
) : BaseViewModel(contextProvider) {

    private val _state = MutableStateFlow<S>(initialState())
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    protected fun setState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    protected suspend fun sendEffect(effect: E) {
        _effect.send(effect)
    }

    abstract fun initialState(): S

    abstract fun onIntent(intent: I)
}