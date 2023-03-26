package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectionGeneralBinding
import id.co.app.nucocore.databinding.ItemSelectionGroupBinding
import id.co.app.nucocore.domain.entities.view.SelectionGeneral
import id.co.app.nucocore.domain.entities.view.SelectionGroup

class SelectionGroupAdapter(
  private val listener: (SelectionGroup) -> Unit
) : DelegateAdapter<SelectionGroup, SelectionGroupAdapter.SelectionGroupViewHolder>(
  SelectionGroup::class.java
) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectionGroupViewHolder(
      ItemSelectionGroupBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectionGroup, viewHolder: SelectionGroupViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectionGroupViewHolder(
    private val binding: ItemSelectionGroupBinding
  ) : BaseViewHolder<SelectionGroup>(binding.root) {

    override fun bind(data: SelectionGroup) {
      binding.model = data
      binding.executePendingBindings()

      binding.buttonAction.setOnClickListener {
        listener.invoke(data)
      }
    }
  }
}