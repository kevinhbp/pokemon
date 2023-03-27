package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemPokemonAbilityBinding
import id.co.app.nucocore.domain.entities.view.PokeAbilityModel
import id.co.app.nucocore.extension.pokemon.formatName

class PokeAbilityAdapter : DelegateAdapter<PokeAbilityModel, PokeAbilityAdapter.PokeAbilityViewHolder>(
  PokeAbilityModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeAbilityViewHolder(
      ItemPokemonAbilityBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeAbilityModel, viewHolder: PokeAbilityViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeAbilityViewHolder(
    private val binding: ItemPokemonAbilityBinding
  ) : BaseViewHolder<PokeAbilityModel>(binding.root) {
    override fun bind(data: PokeAbilityModel) {
      val textLabel = binding.textLabel
      val type = data.name
      textLabel.text = type
    }
  }

}