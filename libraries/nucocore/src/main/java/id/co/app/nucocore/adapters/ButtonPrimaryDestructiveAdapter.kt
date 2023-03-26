package id.co.app.nucocore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemButtonPrimaryDesctructiveBinding
import id.co.app.nucocore.domain.entities.row.ButtonPrimaryDestructiveModel
import id.co.app.nucocore.singleton.LOG_TAG

class ButtonPrimaryDestructiveAdapter(
  private val onClickListener: (ButtonPrimaryDestructiveModel) -> Unit
) : DelegateAdapter<ButtonPrimaryDestructiveModel, ButtonPrimaryDestructiveAdapter.ItemButtonPrimaryDestructiveViewHolder>
  (ButtonPrimaryDestructiveModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ItemButtonPrimaryDestructiveViewHolder(
    ItemButtonPrimaryDesctructiveBinding.inflate(
      LayoutInflater.from(parent.context),
      parent, false
    )
  )

  // --
  override fun bindViewHolder(model: ButtonPrimaryDestructiveModel, viewHolder: ItemButtonPrimaryDestructiveViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class ItemButtonPrimaryDestructiveViewHolder(
    private val binding: ItemButtonPrimaryDesctructiveBinding
  ) : BaseViewHolder<ButtonPrimaryDestructiveModel>(binding.root) {

    override fun bind(data: ButtonPrimaryDestructiveModel) {
      Log.d(LOG_TAG, "Button Primary Destructive : ${data.label} loading ? ${data.loading} | enabled ? ${data.enable}")
      binding.model = data
      binding.buttonView.setOnClickListener {
        onClickListener.invoke(data)
      }
      binding.executePendingBindings()
    }
  }
}