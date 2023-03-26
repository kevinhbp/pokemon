package id.co.app.nucocore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.BaseViewHolder
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapter
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.databinding.ItemSelectionDateBinding
import id.co.app.nucocore.databinding.ItemSelectionGeneralBinding
import id.co.app.nucocore.domain.entities.view.SelectionDate
import id.co.app.nucocore.domain.entities.view.SelectionGeneral
import id.co.app.nucocore.extension.toLong
import id.co.app.nucocore.singleton.LOG_TAG

class SelectionDateAdapter(
  private val listener: (Long?) -> Unit
) : DelegateAdapter<SelectionDate, SelectionDateAdapter.SelectionDateViewHolder>(
  SelectionDate::class.java
) {

  // --
  override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    SelectionDateViewHolder(
      ItemSelectionDateBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  // --
  override fun bindViewHolder(model: SelectionDate, viewHolder: SelectionDateViewHolder, payloads: List<DelegateAdapterItem.Payloadable>) {
    viewHolder.bind(model)
  }

  // --
  inner class SelectionDateViewHolder(
    private val binding: ItemSelectionDateBinding
  ) : BaseViewHolder<SelectionDate>(binding.root) {

    override fun bind(data: SelectionDate) {
      binding.model = data
      binding.executePendingBindings()
      val calView = binding.calendarView

      calView.apply {
        minDate = data.startDate
        maxDate = data.endDate
        if (data.selectedDate != null) {
          setDate(data.selectedDate, false, true)
        }
      }

      if (data.selectedDate != null) {
        listener.invoke(data.selectedDate)
      } else {
        listener.invoke(System.currentTimeMillis())
      }

      calView.setOnDateChangeListener { _, year, month, dayOfMonth ->
        val correctMonth = month + 1
        val theDate = "$dayOfMonth/$correctMonth/$year".toLong("dd/MM/yyyy")
        listener.invoke(theDate)
      }
    }
  }
}