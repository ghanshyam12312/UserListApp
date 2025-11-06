package ghanshyam.demo.anzusersapp.core.errorhandling


enum class ErrorType {
    NETWORK_ERROR,
    UNKNOWN,

}

class AppExceptions(val customError: CustomError?, val errorType: ErrorType?) :
    Throwable(customError?.errorMessage)

fun Throwable.toCustomError(): CustomError {
    return CustomError(
        errorCode = "",
        errorMessage = this.message,
        metadata = this.localizedMessage
    )
}


fun Exception.toCustomError(): CustomError {
    return CustomError(
        errorCode = "",
        errorMessage = this.message,
        metadata = this.localizedMessage
    )
}

fun String.toCustomError(): CustomError {
    return CustomError(
        errorCode = "",
        errorMessage = this,
        metadata = this
    )
}