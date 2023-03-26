package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowSpaceBinding
import id.co.app.nucocore.domain.entities.row.SpaceModel

class SpaceAdapter : DelegateAdapter<SpaceModel, SpaceAdapter.SpaceViewHolder>(SpaceModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return SpaceViewHolder(
      RowSpaceBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: SpaceModel, viewHolder: SpaceViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class SpaceViewHolder(
    private val binding: RowSpaceBinding
  ) : BaseViewHolder<SpaceModel>(binding.root) {

    override fun bind(data: SpaceModel) {
      binding.model = data
      binding.executePendingBindings()
    }
  }
}