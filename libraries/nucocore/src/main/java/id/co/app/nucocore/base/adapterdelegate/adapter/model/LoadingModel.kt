package id.co.app.nucocore.base.adapterdelegate.adapter.model

import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem

class LoadingModel (
	val type: Type = Type.PROGRESS_BAR
): DelegateAdapterItem {
	override fun id(): Any = LOADING

	override fun content(): Any = LOADING

	enum class Type{
		PROGRESS_BAR, VERTICAL_SHIMMER, HORIZONTAL_SHIMMER
	}

	companion object {
		private const val LOADING = "Loading_Model"
	}
}