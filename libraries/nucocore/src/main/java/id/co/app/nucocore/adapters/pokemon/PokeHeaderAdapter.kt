package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonHeaderBinding
import id.co.app.nucocore.databinding.ItemPokemonTypeBinding
import id.co.app.nucocore.domain.entities.view.PokeHeaderModel
import id.co.app.nucocore.domain.entities.view.PokeTypeModel
import id.co.app.nucocore.extension.pokemon.ColorPokemon
import id.co.app.nucocore.extension.pokemon.IconPokemonResId
import id.co.app.nucocore.extension.pokemon.formatName
import id.co.app.nucocore.extension.toThousandFormat

class PokeHeaderAdapter : DelegateAdapter<PokeHeaderModel, PokeHeaderAdapter.PokeHeaderViewHolder>(
  PokeHeaderModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeHeaderViewHolder(
      ItemPokemonHeaderBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeHeaderModel, viewHolder: PokeHeaderViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeHeaderViewHolder(
    private val binding: ItemPokemonHeaderBinding
  ) : BaseViewHolder<PokeHeaderModel>(binding.root) {
    override fun bind(data: PokeHeaderModel) {
      val textView = binding.textDescription
      val count = data.count.toThousandFormat()
      val message = "All generation totaling\n$count pokémon."
      textView.text = message
    }
  }

}