package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemBannerDefaultBinding
import id.co.app.nucocore.domain.entities.row.DefaultBannerModel

class DefaultBannerAdapter(
  private val clickListener: (DefaultBannerModel) -> Unit
) : DelegateAdapter<DefaultBannerModel, DefaultBannerAdapter.DefaultBannerViewHolder>(DefaultBannerModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return DefaultBannerViewHolder(
      ItemBannerDefaultBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  // --
  override fun bindViewHolder(model: DefaultBannerModel, viewHolder: DefaultBannerViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class DefaultBannerViewHolder(
    private val binding: ItemBannerDefaultBinding
  ) : BaseViewHolder<DefaultBannerModel>(binding.root) {

    override fun bind(data: DefaultBannerModel) {
      binding.model = data
      binding.buttonAction.setOnClickListener {
        clickListener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}