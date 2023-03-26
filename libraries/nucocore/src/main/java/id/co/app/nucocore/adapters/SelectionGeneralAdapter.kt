package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectionGeneralBinding
import id.co.app.nucocore.domain.entities.view.SelectionGeneral

class SelectionGeneralAdapter(
  private val listener: (SelectionGeneral) -> Unit
) : DelegateAdapter<SelectionGeneral, SelectionGeneralAdapter.SelectionGeneralViewHolder>(
  SelectionGeneral::class.java
) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectionGeneralViewHolder(
      ItemSelectionGeneralBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectionGeneral, viewHolder: SelectionGeneralViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectionGeneralViewHolder(
    private val binding: ItemSelectionGeneralBinding
  ) : BaseViewHolder<SelectionGeneral>(binding.root) {

    override fun bind(data: SelectionGeneral) {
      binding.model = data
      binding.executePendingBindings()

      binding.buttonAction.setOnClickListener {
        listener.invoke(data)
      }
    }
  }
}