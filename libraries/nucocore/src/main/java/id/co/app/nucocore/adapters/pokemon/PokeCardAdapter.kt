package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonCardBinding
import id.co.app.nucocore.databinding.ItemPokemonTypeBinding
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.view.ActionButtonModel
import id.co.app.nucocore.domain.entities.view.PokeCardModel
import id.co.app.nucocore.domain.entities.view.PokeTypeModel
import id.co.app.nucocore.domain.repository.MainRepository
import id.co.app.nucocore.extension.pokemon.*

class PokeCardAdapter(
  private val loadDetail: (name: String, onResult: (Pokemon) -> Unit) -> Unit,
  private val clickListener: (ActionButtonModel) -> Unit
) : DelegateAdapter<PokeCardModel, PokeCardAdapter.PokeCardViewHolder>(
  PokeCardModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeCardViewHolder(
      ItemPokemonCardBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeCardModel, viewHolder: PokeCardViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeCardViewHolder(
    private val binding: ItemPokemonCardBinding
  ) : BaseViewHolder<PokeCardModel>(binding.root) {

    private val mAdapter by lazy {
      CompositeAdapter.Builder()
        .add(PokeTypeAdapter())
        .build()
    }

    override fun bind(data: PokeCardModel) {
      val imagePhotoView = binding.imagePoke
      val textNumber = binding.textNumber
      val textName = binding.textName
      val rvTypes = binding.rvTypes

      val photoUrl = getPokeSpritesById(data.pokeId)
      val number = data.pokeId.formatId()
      val name = data.pokeName.formatName()

      ViewBinding.bindImageFromUrlCenterInside(imagePhotoView, photoUrl)

      textNumber.text = number
      textName.text = name

      val mLayoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
      rvTypes.apply {
        adapter = mAdapter
        layoutManager = mLayoutManager
      }

      val typeList = arrayListOf<DelegateAdapterItem>()
      data.types.forEach { type ->
        typeList.add(PokeTypeModel(type))
      }
      mAdapter.submitList(typeList.toMutableList())
    }
  }

}