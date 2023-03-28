package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemPokemonEvoBBinding
import id.co.app.nucocore.domain.entities.view.PokeEvoBModel

class PokeEvoBAdapter : DelegateAdapter<PokeEvoBModel, PokeEvoBAdapter.PokeEvoBViewHolder>(
  PokeEvoBModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeEvoBViewHolder(
      ItemPokemonEvoBBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeEvoBModel, viewHolder: PokeEvoBViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeEvoBViewHolder(
    private val binding: ItemPokemonEvoBBinding
  ) : BaseViewHolder<PokeEvoBModel>(binding.root) {
    override fun bind(data: PokeEvoBModel) {
      val textDescription = binding.textDescription
      textDescription.text = data.description
    }
  }

}