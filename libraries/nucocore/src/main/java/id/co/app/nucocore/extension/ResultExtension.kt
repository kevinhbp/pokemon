package id.co.app.nucocore.extension

import id.co.app.nucocore.base.Result

/**
 * Check whether Result is successful or failed
 * @return true if Result.Success
 */
inline val <R> Result<R>.isSuccessful: Boolean
  get() = this is Result.Success

/**
 * Check whether Result is successful or failed
 * @return true if Result.Failure
 */
inline val <R> Result<R>.isFailure: Boolean
  get() = this is Result.Failure
/**
 * Check whether Result is loading
 * @return true if Result.Loading
 */
inline val <R> Result<R>.isLoading: Boolean
  get() = this is Result.Loading

/**
 * @return the value or null if it is a Result.Failure
 */
inline val <R> Result<R>.valueOrNull: R?
  get() = if (this is Result.Success) value else null

/**
 * @return the value or throw an exception if failure
 */
inline val <R> Result<R?>.valueOrThrow: R
  get() = if (this is Result.Success) value!!
  else throw throwableOrNull ?: NullPointerException("value == null")

/**
 * @return throwable value or null of the Result<R>
 */
inline val <R> Result<R>.throwableOrNull: Throwable?
  get() = if (this is Result.Failure) throwable else null

/**
 * The block() will be called when it is a success
 */
inline fun <R> Result<R>.onSuccess(block: (R) -> Unit) {
  if (this is Result.Success) {
    block(value)
  }
}

/**
 * The block() will be called when it is a success
 */
inline fun <R> Result<R>.onLoading(block: () -> Unit) {
  if (this is Result.Loading) {
    block()
  }
}

/**
 * The block() will be called when it is a failure
 */
inline fun <R> Result<R>.onFailure(block: (Throwable?) -> Unit) {
  if (this is Result.Failure) {
    block(throwable)
  }
}

/**
 * Map successful value into something else.
 * @return a new transformed Result.
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> Result.Success(transform(value))
    is Result.Loading -> this
  }
}

/**
 * Map Result successful value into something else.
 * @return a new transformed Result.
 */
inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> transform(value)
    is Result.Loading -> this
  }
}

/**
 * Map an throwable into another throwable
 * @return a new transform Result
 */
inline fun <T, R : Throwable> Result<T>.failureMap(transform: (Throwable?) -> R): Result<T> {
  return when (this) {
    is Result.Failure -> Result.Failure(transform(throwable))
    is Result.Success -> this
    is Result.Loading -> this
  }
}

/**
 * Map Result failure's throwable into another throwable
 * @return a new transform Result
 */
inline fun <T> Result<T>.failureFlatMap(transform: (Throwable?) -> Result.Failure): Result<T> {
  return when (this) {
    is Result.Failure -> transform(throwable)
    is Result.Success -> this
    is Result.Loading -> this
  }
}

/**
 * Get value or default value when failed.
 * @return value or default value.
 * @param defaultValue a default to be return when failed.
 */
inline fun <R> Result<R>.valueOrDefault(defaultValue: (Throwable?) -> R): R {
  return when (this) {
    is Result.Failure -> defaultValue(throwable)
    is Result.Success -> value
    is Result.Loading -> defaultValue(Throwable(""))
  }
}

/**
 * @return an instance of Result
 */
inline fun <R> resultOf(block: () -> R): Result<R> {
  return try {
    Result.Success(block())
  } catch (throwable: Throwable) {
    Result.Failure(throwable)
  }
}

/**
 * Convert a nullable Result into a non-null Result.
 */
@Suppress("UNCHECKED_CAST")
inline fun <R> Result<R?>.failureOnNull(): Result<R> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> {
      if (value == null) Result.Failure(NullPointerException())
      else this as Result<R>
    }
    is Result.Loading -> this
  }
}

/**
 * Create a Result<T> from a standard Result<T>.
 * @return Result<T>
 */
inline fun <T> Result<T>.asResult(): Result<T> = resultOf { valueOrThrow }