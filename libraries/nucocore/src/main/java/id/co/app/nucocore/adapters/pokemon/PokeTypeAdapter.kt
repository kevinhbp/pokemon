package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonTypeBinding
import id.co.app.nucocore.domain.entities.view.PokeTypeModel
import id.co.app.nucocore.extension.pokemon.ColorPokemon
import id.co.app.nucocore.extension.pokemon.IconPokemonResId
import id.co.app.nucocore.extension.pokemon.formatName

class PokeTypeAdapter : DelegateAdapter<PokeTypeModel, PokeTypeAdapter.PokeTypeViewHolder>(
  PokeTypeModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeTypeViewHolder(
      ItemPokemonTypeBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeTypeModel, viewHolder: PokeTypeViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeTypeViewHolder(
    private val binding: ItemPokemonTypeBinding
  ) : BaseViewHolder<PokeTypeModel>(binding.root) {
    override fun bind(data: PokeTypeModel) {
      val imageBg = binding.imageBackground
      val imageIcon = binding.imageIcon
      val textLabel = binding.textLabel

      val type = data.name
      val bgColor = ColorPokemon.get(type)
      val icon = IconPokemonResId.get(type)

      textLabel.text = type.formatName()
      ViewBinding.bindImageResId(imageIcon, icon)
      ViewBinding.bindBackgroundTint(imageBg, bgColor)
    }
  }

}