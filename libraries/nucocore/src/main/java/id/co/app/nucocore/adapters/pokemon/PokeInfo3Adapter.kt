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
import id.co.app.nucocore.databinding.ItemPokemonInfo3Binding
import id.co.app.nucocore.domain.entities.view.*
import id.co.app.nucocore.extension.pokemon.formatName
import id.co.app.nucocore.extension.pokemon.formatStatName
import id.co.app.nucocore.extension.pokemon.getPokeSpritesById
import id.co.app.nucocore.extension.toThousandFormat

class PokeInfo3Adapter : DelegateAdapter<PokeInfo3Model, PokeInfo3Adapter.PokeInfo3ViewHolder>(
  PokeInfo3Model::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeInfo3ViewHolder(
      ItemPokemonInfo3Binding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: PokeInfo3Model, viewHolder: PokeInfo3ViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  inner class PokeInfo3ViewHolder(
    private val binding: ItemPokemonInfo3Binding
  ) : BaseViewHolder<PokeInfo3Model>(binding.root) {

    private val mAdapter by lazy {
      CompositeAdapter.Builder()
        .add(PokeEvoAAdapter())
        .add(PokeEvoBAdapter())
        .build()
    }

    override fun bind(data: PokeInfo3Model) {
      val context = binding.root.context

      val rvEvo = binding.rvEvolution
      val mLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
      rvEvo.apply {
        adapter = mAdapter
        layoutManager = mLayoutManager
      }
      mAdapter.submitList(data.evoList.toMutableList())
    }
  }

}