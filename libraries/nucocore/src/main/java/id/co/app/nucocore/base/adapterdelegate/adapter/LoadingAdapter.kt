package id.co.app.nucocore.base.adapterdelegate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.R
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.base.adapterdelegate.adapter.model.LoadingModel
import id.co.app.nucocore.databinding.ItemLoadingBinding

class LoadingAdapter : DelegateAdapter<LoadingModel, LoadingAdapter.LoadingItemViewHolder>(
	LoadingModel::class.java
) {
	override fun createViewHolder(parent: ViewGroup): LoadingItemViewHolder {
		return LoadingItemViewHolder(
			DataBindingUtil.inflate(
				LayoutInflater.from(parent.context), R.layout.item_loading, parent, false
			)
		)
	}

	override fun bindViewHolder(
        model: LoadingModel,
        viewHolder: LoadingItemViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
	) {
		viewHolder.bind(model)
	}

	inner class LoadingItemViewHolder(
		private val binding: ItemLoadingBinding,
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(model: LoadingModel) {
			binding.progressBar.isVisible = model.type == LoadingModel.Type.PROGRESS_BAR
			binding.verticalShimmer.isVisible = model.type == LoadingModel.Type.VERTICAL_SHIMMER
			binding.horizontalShimmer.isVisible = model.type == LoadingModel.Type.HORIZONTAL_SHIMMER
		}
	}
}