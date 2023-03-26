package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemBannerDefaultBinding
import id.co.app.nucocore.databinding.ItemBannerZoomBinding
import id.co.app.nucocore.domain.entities.row.DefaultBannerModel
import id.co.app.nucocore.domain.entities.row.ZoomBannerModel

class ZoomBannerAdapter(
  private val clickListener: (ZoomBannerModel) -> Unit
) : DelegateAdapter<ZoomBannerModel, ZoomBannerAdapter.ZoomBannerViewHolder>(ZoomBannerModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return ZoomBannerViewHolder(
      ItemBannerZoomBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  // --
  override fun bindViewHolder(model: ZoomBannerModel, viewHolder: ZoomBannerViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class ZoomBannerViewHolder(
    private val binding: ItemBannerZoomBinding
  ) : BaseViewHolder<ZoomBannerModel>(binding.root) {

    override fun bind(data: ZoomBannerModel) {
      binding.model = data
      binding.buttonAction.setOnClickListener {
        clickListener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}