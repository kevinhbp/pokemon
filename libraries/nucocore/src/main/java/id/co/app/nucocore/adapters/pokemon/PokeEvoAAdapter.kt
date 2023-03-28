package id.co.app.nucocore.adapters.pokemon

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonEvoABinding
import id.co.app.nucocore.databinding.ItemPokemonTypeBinding
import id.co.app.nucocore.domain.entities.view.PokeEvoAModel
import id.co.app.nucocore.domain.entities.view.PokeTypeModel
import id.co.app.nucocore.extension.pokemon.*

class PokeEvoAAdapter : DelegateAdapter<PokeEvoAModel, PokeEvoAAdapter.PokeEvoAViewHolder>(
  PokeEvoAModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeEvoAViewHolder(
      ItemPokemonEvoABinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeEvoAModel, viewHolder: PokeEvoAViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeEvoAViewHolder(
    private val binding: ItemPokemonEvoABinding
  ) : BaseViewHolder<PokeEvoAModel>(binding.root) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: PokeEvoAModel) {
      val imagePoke = binding.imagePoke
      val imageUrl = getPokeSpritesById(data.pokeId)
      ViewBinding.bindImageFromUrlCenterInside(imagePoke, imageUrl)

      val textName = binding.textName
      textName.text = data.name.formatName()

      val textNumber = binding.textNumber
      textNumber.text = "# ${data.pokeId.formatId()}"
    }
  }

}