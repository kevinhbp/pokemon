package id.co.app.nucocore.base

import kotlinx.coroutines.CoroutineDispatcher

class BaseDispatchers : Dispatchers {
	override fun io(): CoroutineDispatcher {
		return kotlinx.coroutines.Dispatchers.IO
	}

	override fun ui(): CoroutineDispatcher {
		return kotlinx.coroutines.Dispatchers.Main
	}
}