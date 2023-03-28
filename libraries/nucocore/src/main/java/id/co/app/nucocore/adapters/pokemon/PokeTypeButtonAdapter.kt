package id.co.app.nucocore.adapters.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemPokemonTypeButtonBinding
import id.co.app.nucocore.domain.entities.view.PokeTypeButtonModel
import id.co.app.nucocore.domain.entities.view.PokeTypeModel

class PokeTypeButtonAdapter(
  private val onClick: (type: String) -> Unit,
) : DelegateAdapter<PokeTypeButtonModel, PokeTypeButtonAdapter.PokeTypeButtonViewHolder>(
  PokeTypeButtonModel::class.java
) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return PokeTypeButtonViewHolder(
      ItemPokemonTypeButtonBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(
    model: PokeTypeButtonModel,
    viewHolder: PokeTypeButtonViewHolder,
    payloads: List<DelegateAdapterItem.Payloadable>
  ) {
    viewHolder.bind(model)
  }

  inner class PokeTypeButtonViewHolder(
    private val binding: ItemPokemonTypeButtonBinding
  ) : BaseViewHolder<PokeTypeButtonModel>(binding.root) {

    private val mAdapter by lazy {
      CompositeAdapter.Builder()
        .add(PokeTypeAdapter(onClick))
        .build()
    }

    override fun bind(data: PokeTypeButtonModel) {
      val context = binding.root.context
      val rvContent = binding.rvContent
      val mLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
      rvContent.apply {
        adapter = mAdapter
        layoutManager = mLayoutManager
      }
      val mData = arrayListOf<DelegateAdapterItem>()
      data.types.forEach { name ->
        mData.add(PokeTypeModel(name))
      }
      mAdapter.submitList(mData.toMutableList())
    }
  }

}