package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonSpriteBinding
import id.co.app.nucocore.domain.entities.view.PokeSpriteModel

class PokeSpriteAdapter : DelegateAdapter<PokeSpriteModel, PokeSpriteAdapter.PokeSpriteViewHolder>(
  PokeSpriteModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeSpriteViewHolder(
      ItemPokemonSpriteBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeSpriteModel, viewHolder: PokeSpriteViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeSpriteViewHolder(
    private val binding: ItemPokemonSpriteBinding
  ) : BaseViewHolder<PokeSpriteModel>(binding.root) {
    override fun bind(data: PokeSpriteModel) {
      val imageView = binding.imagePhoto
      ViewBinding.bindImageFromUrlCenterInside(imageView, data.url)
    }
  }

}