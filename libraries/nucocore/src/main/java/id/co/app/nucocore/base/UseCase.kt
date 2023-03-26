package id.co.app.nucocore.base

import kotlinx.coroutines.flow.Flow

abstract class UseCase<T> {
	open fun execute(token: String): Flow<T> {
		TODO("Your default implementation here")
	}

	open fun execute(): Flow<T> {
		TODO("Your default implementation here")
	}
}