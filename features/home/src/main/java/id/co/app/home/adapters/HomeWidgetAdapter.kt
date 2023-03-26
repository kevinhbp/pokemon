package id.co.app.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.home.databinding.LayoutHomeWidgetsBinding
import id.co.app.home.model.HomeWidgetsModel

class HomeWidgetAdapter(
  private val clickListener: (target: String) -> Unit
) : DelegateAdapter<HomeWidgetsModel,
  HomeWidgetAdapter.HomeWidgetsViewHolder>
  (HomeWidgetsModel::class.java) {

  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return HomeWidgetsViewHolder(
      LayoutHomeWidgetsBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun bindViewHolder(model: HomeWidgetsModel, viewHolder: HomeWidgetsViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }


  inner class HomeWidgetsViewHolder(
    private val binding: LayoutHomeWidgetsBinding
  ) : BaseViewHolder<HomeWidgetsModel>(binding.root) {
    override fun bind(data: HomeWidgetsModel) {
      binding.model = data
      binding.buttonDataSync.setOnClickListener {
        clickListener.invoke(data.dataSyncClickTarget)
      }
      binding.buttonNotifications.setOnClickListener {
        clickListener.invoke(data.notificationsClickTarget)
      }
      binding.executePendingBindings()
    }
  }
}