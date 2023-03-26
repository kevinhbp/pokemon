package id.co.app.nucocore.base.adapterdelegate

import android.util.SparseArray
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

class CompositePagingAdapter(
	private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>>
) : PagingDataAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>(DelegateAdapterItemDiffCallback()) {
	override fun getItemViewType(position: Int): Int {
		for (i in 0 until delegates.size()) {
			if (delegates[i].modelClass == getItem(position)?.javaClass) {
				return delegates.keyAt(i)
			}
		}
		throw NullPointerException("Can not get viewType for position $position")
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		delegates[viewType].createViewHolder(parent)

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
		onBindViewHolder(holder, position, mutableListOf())

	override fun onBindViewHolder(
		holder: RecyclerView.ViewHolder,
		position: Int,
		payloads: MutableList<Any>
	) {
		val delegateAdapter = delegates[getItemViewType(position)]

		if (delegateAdapter != null) {
			val delegatePayloads = payloads.map { it as DelegateAdapterItem.Payloadable }
			getItem(position)?.let { delegateAdapter.bindViewHolder(it, holder, delegatePayloads) }
		} else {
			throw NullPointerException("can not find adapter for position $position")
		}
	}

	override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
		delegates[holder.itemViewType].onViewRecycled(holder)
		super.onViewRecycled(holder)
	}

	override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
		delegates[holder.itemViewType].onViewDetachedFromWindow(holder)
		super.onViewDetachedFromWindow(holder)
	}

	override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
		delegates[holder.itemViewType].onViewAttachedToWindow(holder)
		super.onViewAttachedToWindow(holder)
	}

	class Builder {

		private var count: Int = 0
		private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>> =
			SparseArray()

		fun add(delegateAdapter: DelegateAdapter<out DelegateAdapterItem, *>): Builder {
			delegates.put(
				count++,
				delegateAdapter as DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>
			)
			return this
		}

		fun build(): CompositePagingAdapter {
			require(count != 0) { "Register at least one adapter" }
			return CompositePagingAdapter(delegates)
		}
	}
}