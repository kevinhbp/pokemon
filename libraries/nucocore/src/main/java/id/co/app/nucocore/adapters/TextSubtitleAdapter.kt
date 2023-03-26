package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowTextSubtitleBinding
import id.co.app.nucocore.domain.entities.row.SubtitleTextModel

class TextSubtitleAdapter(
  private val clickListener: (SubtitleTextModel) -> Unit
) : DelegateAdapter<SubtitleTextModel,
  TextSubtitleAdapter.RowTextSubtitleViewHolder>
  (SubtitleTextModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return RowTextSubtitleViewHolder(
      RowTextSubtitleBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
      clickListener
    )
  }

  override fun bindViewHolder(
    model: SubtitleTextModel, viewHolder: RowTextSubtitleViewHolder,
    payloads: List<DelegateAdapterItem.Payloadable>
  ) {
    viewHolder.bind(model)
  }

  inner class RowTextSubtitleViewHolder(
    private val binding: RowTextSubtitleBinding,
    private val clickListener: (SubtitleTextModel) -> Unit
  ) : BaseViewHolder<SubtitleTextModel>(binding.root) {

    override fun bind(data: SubtitleTextModel) {
      binding.model = data
      binding.text1.setOnClickListener {
        clickListener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}