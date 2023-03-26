package id.co.app.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.profile.databinding.LayoutProfileHeaderBinding
import id.co.app.profile.model.HeaderProfileModel

class ProfileHeaderAdapter : DelegateAdapter
<HeaderProfileModel, ProfileHeaderAdapter.ProfileHeaderViewHolder>
  (HeaderProfileModel::class.java) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ProfileHeaderViewHolder(
    LayoutProfileHeaderBinding.inflate(
      LayoutInflater.from(parent.context),
      parent, false
    )
  )

  // --
  override fun bindViewHolder(model: HeaderProfileModel, viewHolder: ProfileHeaderViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class ProfileHeaderViewHolder(
    private val binding: LayoutProfileHeaderBinding
  ) : BaseViewHolder<HeaderProfileModel>(binding.root) {

    override fun bind(data: HeaderProfileModel) {
      binding.model = data
      binding.executePendingBindings()
    }
  }
}