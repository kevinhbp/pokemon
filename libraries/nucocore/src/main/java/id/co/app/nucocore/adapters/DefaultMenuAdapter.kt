package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemMenuDefaultBinding
import id.co.app.nucocore.domain.entities.row.DefaultMenuItem

class DefaultMenuAdapter(
  private val onClickListener: (DefaultMenuItem) -> Unit
) : DelegateAdapter<DefaultMenuItem, DefaultMenuAdapter.ItemMenuDefaultViewHolder>
  (DefaultMenuItem::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ItemMenuDefaultViewHolder(
    ItemMenuDefaultBinding.inflate(
      LayoutInflater.from(parent.context),
      parent, false
    )
  )

  // --
  override fun bindViewHolder(model: DefaultMenuItem, viewHolder: ItemMenuDefaultViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class ItemMenuDefaultViewHolder(
    private val binding: ItemMenuDefaultBinding
  ) : BaseViewHolder<DefaultMenuItem>(binding.root) {

    override fun bind(data: DefaultMenuItem) {
      binding.model = data
      binding.executePendingBindings()
    }
  }
}