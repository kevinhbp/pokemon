package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowTextSubtitleSmallBinding
import id.co.app.nucocore.domain.entities.row.SubtitleSmallTextModel
import id.co.app.nucocore.domain.entities.row.SubtitleTextModel

class TextSubtitleSmallAdapter : DelegateAdapter<SubtitleSmallTextModel,
  TextSubtitleSmallAdapter.SubtitleSmallViewHolder>
  (SubtitleSmallTextModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = SubtitleSmallViewHolder(
    RowTextSubtitleSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  )

  override fun bindViewHolder(model: SubtitleSmallTextModel, viewHolder: SubtitleSmallViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class SubtitleSmallViewHolder(
    private val binding: RowTextSubtitleSmallBinding
  ) : BaseViewHolder<SubtitleSmallTextModel>(binding.root) {

    override fun bind(data: SubtitleSmallTextModel) {
      binding.model = data
      binding.executePendingBindings()
    }
  }
}