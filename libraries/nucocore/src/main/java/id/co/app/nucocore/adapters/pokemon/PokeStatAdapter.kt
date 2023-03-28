package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonSpriteBinding
import id.co.app.nucocore.databinding.ItemPokemonStatBinding
import id.co.app.nucocore.domain.entities.view.PokeSpriteModel
import id.co.app.nucocore.domain.entities.view.PokeStatModel

class PokeStatAdapter : DelegateAdapter<PokeStatModel, PokeStatAdapter.PokeStatViewHolder>(
  PokeStatModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeStatViewHolder(
      ItemPokemonStatBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(
    model: PokeStatModel,
    viewHolder: PokeStatViewHolder,
    payloads: List<DelegateAdapterItem.Payloadable>
  ) {
    viewHolder.bind(model)
  }

  inner class PokeStatViewHolder(
    private val binding: ItemPokemonStatBinding
  ) : BaseViewHolder<PokeStatModel>(binding.root) {
    override fun bind(data: PokeStatModel) {
      val tvName = binding.textStatName
      val tvValue = binding.textStatValue
      tvName.text = data.name
      tvValue.text = data.value
    }
  }

}