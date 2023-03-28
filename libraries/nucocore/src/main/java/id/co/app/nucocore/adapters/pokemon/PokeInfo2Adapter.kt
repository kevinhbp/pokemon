package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.binding.ViewBinding
import id.co.app.nucocore.databinding.ItemPokemonInfo1Binding
import id.co.app.nucocore.databinding.ItemPokemonInfo2Binding
import id.co.app.nucocore.domain.entities.view.*
import id.co.app.nucocore.extension.pokemon.formatName
import id.co.app.nucocore.extension.pokemon.formatStatName
import id.co.app.nucocore.extension.pokemon.getPokeSpritesById
import id.co.app.nucocore.extension.toThousandFormat

class PokeInfo2Adapter : DelegateAdapter<PokeInfo2Model, PokeInfo2Adapter.PokeInfo2ViewHolder>(
  PokeInfo2Model::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeInfo2ViewHolder(
      ItemPokemonInfo2Binding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeInfo2Model, viewHolder: PokeInfo2ViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeInfo2ViewHolder(
    private val binding: ItemPokemonInfo2Binding
  ) : BaseViewHolder<PokeInfo2Model>(binding.root) {

    private val mAdapterSprites by lazy {
      CompositeAdapter.Builder()
        .add(PokeSpriteAdapter())
        .build()
    }

    private val mAdapterStat by lazy {
      CompositeAdapter.Builder()
        .add(PokeStatAdapter())
        .build()
    }

    override fun bind(data: PokeInfo2Model) {
      val context = binding.root.context

      /// --- SPRITES
      val rvSprites = binding.rvOtherImages
      val mLayoutManagerSprites = GridLayoutManager(context, 2)
      rvSprites.apply {
        adapter = mAdapterSprites
        layoutManager = mLayoutManagerSprites
      }
      val spritesData = arrayListOf<DelegateAdapterItem>()
      data.otherImages.forEach { url ->
        spritesData.add(PokeSpriteModel(url))
      }
      mAdapterSprites.submitList(spritesData.toMutableList())

      /// --- STAT
      val rvStat = binding.rvStats
      val mLayoutManagerStat = GridLayoutManager(context, 3)
      rvStat.apply {
        adapter = mAdapterStat
        layoutManager = mLayoutManagerStat
      }
      val statData = arrayListOf<DelegateAdapterItem>()
      data.stats.forEach { stat ->
        val name = stat.stat.name.formatStatName()
        val value = stat.baseStat.toThousandFormat()
        statData.add(PokeStatModel(name, value))
      }
      mAdapterStat.submitList(statData.toMutableList())
    }
  }

}