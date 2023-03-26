package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowTextSubtitleWithButtonBinding
import id.co.app.nucocore.domain.entities.row.SubtitleTextWithButtonModel

class TextSubtitleWithButtonAdapter(
  private val clickListener: (SubtitleTextWithButtonModel) -> Unit
) : DelegateAdapter<SubtitleTextWithButtonModel,
  TextSubtitleWithButtonAdapter.RowTextSubtitleViewHolder>
  (SubtitleTextWithButtonModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return RowTextSubtitleViewHolder(
      RowTextSubtitleWithButtonBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
      clickListener
    )
  }

  override fun bindViewHolder(
    model: SubtitleTextWithButtonModel, viewHolder: RowTextSubtitleViewHolder,
    payloads: List<DelegateAdapterItem.Payloadable>
  ) {
    viewHolder.bind(model)
  }

  inner class RowTextSubtitleViewHolder(
    private val binding: RowTextSubtitleWithButtonBinding,
    private val clickListener: (SubtitleTextWithButtonModel) -> Unit
  ) : BaseViewHolder<SubtitleTextWithButtonModel>(binding.root) {

    override fun bind(data: SubtitleTextWithButtonModel) {
      binding.model = data
      binding.buttonAction.setOnClickListener {
        clickListener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}