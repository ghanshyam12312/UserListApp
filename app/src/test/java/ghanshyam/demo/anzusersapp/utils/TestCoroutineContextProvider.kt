package ghanshyam.demo.anzusersapp.utils

import ghanshyam.demo.anzusersapp.core.viewmodel.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class TestCoroutineContextProvider(
    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()
) : CoroutineContextProvider {

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override fun reset() {
        // Optional: If you want to reset or cleanup test dispatcher
    }
}