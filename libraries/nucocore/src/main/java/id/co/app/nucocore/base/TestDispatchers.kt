package id.co.app.nucocore.base

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatchers : Dispatchers {
	override fun io(): CoroutineDispatcher {
		return kotlinx.coroutines.Dispatchers.Unconfined
	}

	override fun ui(): CoroutineDispatcher {
		return kotlinx.coroutines.Dispatchers.Unconfined
	}
}