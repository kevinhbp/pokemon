package id.co.app.nucocore.adapters.pokemon

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.R.color
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
      textLabel.text = type.formatName()

      val imageView = binding.imageIcon
      val tintId = if (data.hidden) color.nuco_gray_3 else color.nuco_gray_1
      val tint = ContextCompat.getColor(imageView.context, tintId)
      setImageTint(imageView, tint)
    }

    private fun setImageTint(view: ImageView, color: Int) {
      val defaultStateList = arrayOf(
        IntArray(1) { android.R.attr.state_enabled },
        IntArray(1) { -android.R.attr.state_enabled },
      )
      val colorList = IntArray(2).apply {
        this[0] = color
        this[1] = color
      }
      view.imageTintList = ColorStateList(defaultStateList, colorList)
    }
  }

}