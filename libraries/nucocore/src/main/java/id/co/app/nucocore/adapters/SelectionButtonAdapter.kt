package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectionButtonBinding
import id.co.app.nucocore.databinding.ItemSelectorWithAButtonBinding
import id.co.app.nucocore.domain.entities.row.SelectionButtonModel
import id.co.app.nucocore.domain.entities.row.SelectorButtonModel

class SelectionButtonAdapter(
  private val listener: (SelectionButtonModel) -> Unit,
) : DelegateAdapter<SelectionButtonModel, SelectionButtonAdapter.SelectionButtonViewHolder>
  (SelectionButtonModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectionButtonViewHolder(
      ItemSelectionButtonBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectionButtonModel, viewHolder: SelectionButtonViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectionButtonViewHolder(
    private val binding: ItemSelectionButtonBinding,
  ) : BaseViewHolder<SelectionButtonModel>(binding.root) {

    override fun bind(data: SelectionButtonModel) {
      binding.model = data
      binding.buttonAction.setOnClickListener {
        listener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}