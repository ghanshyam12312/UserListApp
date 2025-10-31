package ghanshyam.demo.anzusersapp.core.errorhandling

data class CustomError(
    val errorCode: String? = null,
    val metadata: String? = null,
    val errorMessage: String? = null,
    val errorTitle: String? = null,
    val displayMessage: String? = null
)