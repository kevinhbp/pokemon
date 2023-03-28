package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.R.string
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemPokemonTypeHeaderBinding
import id.co.app.nucocore.domain.entities.view.PokeTypeHeaderModel
import id.co.app.nucocore.extension.pokemon.formatName

class PokeTypeHeaderAdapter : DelegateAdapter<PokeTypeHeaderModel, PokeTypeHeaderAdapter.PokeTypeHeaderViewHolder>(
  PokeTypeHeaderModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeTypeHeaderViewHolder(
      ItemPokemonTypeHeaderBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeTypeHeaderModel, viewHolder: PokeTypeHeaderViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeTypeHeaderViewHolder(
    private val binding: ItemPokemonTypeHeaderBinding
  ) : BaseViewHolder<PokeTypeHeaderModel>(binding.root) {
    override fun bind(data: PokeTypeHeaderModel) {
      val context = binding.root.context
      val textView = binding.textTitle
      val typeName = data.typeName.formatName()
      val message = context.getString(string.pokemon_with_type, typeName)
      textView.text = message
    }
  }

}