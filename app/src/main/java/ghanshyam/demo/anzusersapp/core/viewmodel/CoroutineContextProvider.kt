package ghanshyam.demo.anzusersapp.core.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

interface CoroutineContextProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher

    fun reset()
}

@Singleton
class CoroutineContextProviderIMPL @Inject constructor() : CoroutineContextProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
    override fun reset() {
        //Nothing to do here
    }
}