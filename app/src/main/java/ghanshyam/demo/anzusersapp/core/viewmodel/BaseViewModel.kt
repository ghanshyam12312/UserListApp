package ghanshyam.demo.anzusersapp.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ghanshyam.demo.anzusersapp.core.errorhandling.AppExceptions
import ghanshyam.demo.anzusersapp.core.errorhandling.ErrorType
import ghanshyam.demo.anzusersapp.core.errorhandling.Logger
import ghanshyam.demo.anzusersapp.core.errorhandling.toCustomError
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.plus

abstract class BaseViewModel(
    protected val contextProvider: CoroutineContextProvider
) : ViewModel() {

    // Builds a CoroutineExceptionHandler to catch exceptions in coroutines and handle them gracefully.
    private fun coroutineExceptionHandler(networkDelegate: ((AppExceptions?) -> Unit)?) =
        CoroutineExceptionHandler { _, t ->
            handleCoroutineException(t, networkDelegate)
        }

    // Handles exceptions thrown within coroutines.
    // Differentiates between CancellationException and other types of exceptions.
    private fun handleCoroutineException(
        error: Throwable,
        networkDelegate: ((AppExceptions?) -> Unit)?
    ) {
        Logger.logError("Coroutine Exception", error.message)
        if (error !is CancellationException) {
            // Forward non-cancellation errors as NETWORK_ERROR
            networkDelegate?.invoke(
                AppExceptions(
                    error.toCustomError(),
                    ErrorType.NETWORK_ERROR
                )
            )
        } else {
            // Treat CancellationException as UNKNOWN to prevent unnecessary crash reporting
            networkDelegate?.invoke(
                AppExceptions(
                    error.toCustomError(),
                    ErrorType.UNKNOWN
                )
            )
        }
    }

    protected fun launchSafe(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(contextProvider.main + coroutineExceptionHandler(null)) {
            block()
        }
    }

    // Launches a coroutine on the main thread with optional progress visibility and error handling.
    protected fun launchWithProgressOnMain(
        handleProgress: Boolean = false,
        block: suspend CoroutineScope.() -> Unit,
        progressDelegate: ((Boolean) -> Unit?)? = null,
        networkErrorDelegate: ((AppExceptions?) -> Unit)? = null
    ): Job {
        return viewModelScope.launch(
            contextProvider.main + coroutineExceptionHandler(networkErrorDelegate)
        ) {
            if (handleProgress) progressDelegate?.invoke(true)
            block()
            if (handleProgress) progressDelegate?.invoke(false)
        }
    }

    // Launches a coroutine on the IO thread with optional progress visibility and error handling.
    protected fun launchInIOWithProgress(
        handleProgress: Boolean = false,
        block: suspend CoroutineScope.() -> Unit,
        progressDelegate: ((Boolean) -> Unit?)? = null,
        networkErrorDelegate: ((AppExceptions?) -> Unit)? = null
    ): Job {
        return viewModelScope.launch(
            contextProvider.io + coroutineExceptionHandler(networkErrorDelegate)
        ) {
            if (handleProgress) progressDelegate?.invoke(true)
            block()
            if (handleProgress) progressDelegate?.invoke(false)
        }
    }
}