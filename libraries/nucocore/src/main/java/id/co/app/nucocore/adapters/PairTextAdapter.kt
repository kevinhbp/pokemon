package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowTextPairDefaultBinding
import id.co.app.nucocore.domain.entities.row.DefaultPairTextModel

class PairTextAdapter :
  DelegateAdapter<DefaultPairTextModel, PairTextAdapter.RowTextPairDefaultViewHolder>
    (DefaultPairTextModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return RowTextPairDefaultViewHolder(
      RowTextPairDefaultBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: DefaultPairTextModel, viewHolder: RowTextPairDefaultViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class RowTextPairDefaultViewHolder(
    private val binding: RowTextPairDefaultBinding
  ) : BaseViewHolder<DefaultPairTextModel>(binding.root) {

    override fun bind(data: DefaultPairTextModel) {
      binding.model = data
      binding.executePendingBindings()
    }
  }
}