package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowEmptyStateDefaultBinding
import id.co.app.nucocore.domain.entities.row.DefaultEmptyStateModel
import id.co.app.nucocore.domain.entities.view.ActionButtonModel

class DefaultEmptyStateAdapter(
  private val clickListener: (ActionButtonModel) -> Unit
) : DelegateAdapter<DefaultEmptyStateModel, DefaultEmptyStateAdapter.DefaultEmptyStateViewHolder>(DefaultEmptyStateModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return DefaultEmptyStateViewHolder(
      RowEmptyStateDefaultBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: DefaultEmptyStateModel, viewHolder: DefaultEmptyStateViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class DefaultEmptyStateViewHolder(
    private val binding: RowEmptyStateDefaultBinding
  ) : BaseViewHolder<DefaultEmptyStateModel>(binding.root) {

    override fun bind(data: DefaultEmptyStateModel) {
      binding.model = data
      binding.buttonPrimary.setOnClickListener {
        if (data.buttonPrimary != null) {
          clickListener.invoke(data.buttonPrimary)
        }
      }
      binding.buttonSecondary.setOnClickListener {
        if (data.buttonSecondary != null) {
          clickListener.invoke(data.buttonSecondary)
        }
      }
      binding.executePendingBindings()
    }
  }
}