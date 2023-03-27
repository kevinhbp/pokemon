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
import id.co.app.nucocore.databinding.ItemPokemonInfo1Binding
import id.co.app.nucocore.domain.entities.view.PokeAbilityModel
import id.co.app.nucocore.domain.entities.view.PokeInfo1Model
import id.co.app.nucocore.domain.entities.view.PokeTypeModel
import id.co.app.nucocore.extension.pokemon.getPokeSpritesById

class PokeInfo1Adapter : DelegateAdapter<PokeInfo1Model, PokeInfo1Adapter.PokeInfo1ViewHolder>(
  PokeInfo1Model::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeInfo1ViewHolder(
      ItemPokemonInfo1Binding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeInfo1Model, viewHolder: PokeInfo1ViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeInfo1ViewHolder(
    private val binding: ItemPokemonInfo1Binding
  ) : BaseViewHolder<PokeInfo1Model>(binding.root) {

    private val mAdapter by lazy {
      CompositeAdapter.Builder()
        .add(PokeTypeAdapter())
        .build()
    }

    private val mAdapter2 by lazy {
      CompositeAdapter.Builder()
        .add(PokeAbilityAdapter())
        .build()
    }

    override fun bind(data: PokeInfo1Model) {
      binding.textName.text = data.pokeName

      ViewBinding.bindImageFromUrlCenterInside(binding.imagePoke, getPokeSpritesById(data.pokeId))

      binding.textWeight.text = data.pokeWeight

      binding.textHeight.text = data.pokeHeight

      // --
      val rvTypes = binding.rvTypes
      val mLayoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
      rvTypes.apply {
        adapter = mAdapter
        layoutManager = mLayoutManager
      }

      val typeList = arrayListOf<DelegateAdapterItem>()
      data.types.forEach { type ->
        typeList.add(PokeTypeModel(type))
      }
      mAdapter.submitList(typeList.toMutableList())

      // --
      val rvAbilities = binding.rvAbilities
      val mLayoutManager2 = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
      rvAbilities.apply {
        adapter = mAdapter2
        layoutManager = mLayoutManager2
      }

      val abilityList = arrayListOf<DelegateAdapterItem>()
      data.abilities.forEach { name ->
        abilityList.add(PokeAbilityModel(name))
      }
      mAdapter2.submitList(abilityList.toMutableList())


    }
  }

}