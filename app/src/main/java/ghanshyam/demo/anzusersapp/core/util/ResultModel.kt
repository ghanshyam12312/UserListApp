package ghanshyam.demo.anzusersapp.core.util

import androidx.annotation.Keep
import ghanshyam.demo.anzusersapp.core.errorhandling.AppExceptions

sealed class ResultModel<out T> {
    data class Loading<T>(val isLoading: Boolean) : ResultModel<T>()
    data class Failure<T>(val exceptions: AppExceptions) : ResultModel<T>()
    data class Success<T>(
        val response: T,
    ) : ResultModel<T>()
    @Keep
    companion object {
        fun <T> loading(isLoading: Boolean): ResultModel<T> = Loading(isLoading)
        fun <T> success(
            response: T,
        ): ResultModel<T> = Success(response)

        fun <T> failure(ex: AppExceptions): ResultModel<T> = Failure(ex)
    }
}
inline fun <T, R> ResultModel<T>.mapResult(transform: (T) -> R): ResultModel<R> {
    return when (this) {
        is ResultModel.Success -> ResultModel.success(transform(this.response))
        is ResultModel.Failure -> ResultModel.failure(this.exceptions)
        is ResultModel.Loading -> ResultModel.loading(this.isLoading)
    }
}