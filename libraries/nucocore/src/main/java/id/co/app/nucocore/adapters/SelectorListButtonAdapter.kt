package id.co.app.nucocore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectorWithButtonListBinding
import id.co.app.nucocore.domain.entities.row.SelectionButtonModel
import id.co.app.nucocore.domain.entities.row.SelectorButtonModel
import id.co.app.nucocore.domain.entities.row.SelectorListButtonModel

class SelectorListButtonAdapter(
  private val listener: (SelectionButtonModel) -> Unit,
) : DelegateAdapter<SelectorListButtonModel, SelectorListButtonAdapter.SelectorListButtonViewHolder>
  (SelectorListButtonModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectorListButtonViewHolder(
      ItemSelectorWithButtonListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectorListButtonModel, viewHolder: SelectorListButtonViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectorListButtonViewHolder(
    private val binding: ItemSelectorWithButtonListBinding,
  ) : BaseViewHolder<SelectorListButtonModel>(binding.root) {

    private lateinit var data: SelectorListButtonModel

    private val mAdapter by lazy {
      CompositeAdapter.Builder()
        .add(SelectionButtonAdapter {
          listener.invoke(it)
          onSelectedSelectionButton(it)
        })
        .build()
    }

    override fun bind(data: SelectorListButtonModel) {
      this.data = data
      binding.model = data
      val mLayoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
      binding.rvButtons.apply {
        adapter = mAdapter
        layoutManager = mLayoutManager
      }
      binding.executePendingBindings()
      mAdapter.submitList(data.buttons)
    }

    private fun onSelectedSelectionButton(model: SelectionButtonModel) {
      var buttons = data.buttons
      buttons = buttons.map {
        it.copy(
          selected = (it.mId == model.mId),
        )
      }
      mAdapter.submitList(buttons)
    }
  }
}