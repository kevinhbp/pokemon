package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.RowTextHeroBinding
import id.co.app.nucocore.databinding.RowTextSubtitleBinding
import id.co.app.nucocore.domain.entities.row.HeroTextModel
import id.co.app.nucocore.domain.entities.row.SubtitleTextModel

class TextHeroAdapter(
  private val clickListener: (HeroTextModel) -> Unit
) : DelegateAdapter<HeroTextModel, TextHeroAdapter.RowTextHeroViewHolder>(HeroTextModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return RowTextHeroViewHolder(
      RowTextHeroBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
      clickListener
    )
  }

  override fun bindViewHolder(model: HeroTextModel, viewHolder: RowTextHeroViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class RowTextHeroViewHolder(
    private val binding: RowTextHeroBinding,
    private val clickListener: (HeroTextModel) -> Unit
  ) : BaseViewHolder<HeroTextModel>(binding.root) {

    override fun bind(data: HeroTextModel) {
      binding.model = data
      binding.text1.setOnClickListener {
        clickListener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}