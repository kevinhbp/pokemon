package id.co.app.nucocore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectorWithASwitchBinding
import id.co.app.nucocore.domain.entities.row.SelectorSwitchModel
import id.co.app.nucocore.singleton.LOG_TAG

class SelectorSwitchAdapter(
  private val listener: (SelectorSwitchModel) -> Unit,
) : DelegateAdapter<SelectorSwitchModel, SelectorSwitchAdapter.SelectorSwitchViewHolder>
  (SelectorSwitchModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectorSwitchViewHolder(
      ItemSelectorWithASwitchBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectorSwitchModel, viewHolder: SelectorSwitchViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectorSwitchViewHolder(
    private val binding: ItemSelectorWithASwitchBinding,
  ) : BaseViewHolder<SelectorSwitchModel>(binding.root) {

    override fun bind(data: SelectorSwitchModel) {
      binding.model = data
      val switchIndex = if (data.switchValue) 0 else 1

      Log.d(LOG_TAG, "Switch index: $switchIndex")

      binding.switchPage.apply {
        setLabel1(data.buttonLabelA)
        setLabel2(data.buttonLabelB)
        setSelected(switchIndex)
        setOnSelectedIndexChangedListener {
          listener.invoke(
            data.copy(
              mId = data.mId,
              label = data.label,
              buttonLabelA = data.buttonLabelA,
              buttonLabelB = data.buttonLabelB,
              switchValue = it > 0
            )
          )
        }
      }
      binding.executePendingBindings()
    }
  }
}