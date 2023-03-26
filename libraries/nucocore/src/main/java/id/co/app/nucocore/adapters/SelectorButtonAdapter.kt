package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectorWithAButtonBinding
import id.co.app.nucocore.domain.entities.row.SelectorButtonModel

class SelectorButtonAdapter(
  private val listener: (SelectorButtonModel) -> Unit,
) : DelegateAdapter<SelectorButtonModel, SelectorButtonAdapter.SelectorButtonViewHolder>
  (SelectorButtonModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectorButtonViewHolder(
      ItemSelectorWithAButtonBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectorButtonModel, viewHolder: SelectorButtonViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectorButtonViewHolder(
    private val binding: ItemSelectorWithAButtonBinding,
  ) : BaseViewHolder<SelectorButtonModel>(binding.root) {

    override fun bind(data: SelectorButtonModel) {
      binding.model = data
      binding.buttonAction.setOnClickListener {
        listener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}