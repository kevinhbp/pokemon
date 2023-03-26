package id.co.app.nucocore.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import java.util.*

class ContainsFilterArrayAdapter<T>(context: Context, @LayoutRes resource: Int, objects: List<T>) : ArrayAdapter<T>(context, resource, objects) {

  private var mObjects: List<T> = objects
  private val mLock = Any()
  private var mOriginalValues: ArrayList<T>? = null
  private var mFilter: ContainsArrayFilter? = null


  override fun getFilter(): Filter {
    if (mFilter == null) {
      mFilter = ContainsArrayFilter()
    }
    return mFilter!!
  }

  inner class ContainsArrayFilter : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
      val results = FilterResults()

      if (mOriginalValues == null) {
        synchronized(mLock) { mOriginalValues = ArrayList<T>(mObjects) }
      }

      if (constraint == null || constraint.isEmpty()) {
        val list: ArrayList<T>
        synchronized(mLock) { list = mOriginalValues?.let { ArrayList<T>(it) } ?: arrayListOf() }
        results.values = list
        results.count = list.size
      } else {
        val constraintString: String = constraint.toString().lowercase(Locale.getDefault())
        val values: ArrayList<T>
        synchronized(mLock) { values = mOriginalValues?.let { ArrayList<T>(it) } ?: arrayListOf() }
        val count = values.size
        val newValues = ArrayList<T>()
        for (i in 0 until count) {
          val value = values[i]
          val valueText = value.toString().lowercase(Locale.getDefault())
          if (valueText.contains(constraintString)) {
            newValues.add(value)
          }
        }
        results.values = newValues
        results.count = newValues.size
      }

      return results
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
      clear()
      if (results != null && results.count > 0) {
        addAll(results.values as List<T>)
      } else {
        addAll(mObjects)
      }
      notifyDataSetChanged()
    }
  }

}