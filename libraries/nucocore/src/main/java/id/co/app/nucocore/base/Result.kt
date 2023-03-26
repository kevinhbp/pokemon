package id.co.app.nucocore.base

sealed class Result<out T> {
    data class Success<out T>(
        val value: T
    ) : Result<T>()

    data class Failure(
        val throwable: Throwable
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
}