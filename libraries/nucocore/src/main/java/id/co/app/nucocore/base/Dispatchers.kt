package id.co.app.nucocore.base

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {
	fun io(): CoroutineDispatcher
	fun ui(): CoroutineDispatcher
}