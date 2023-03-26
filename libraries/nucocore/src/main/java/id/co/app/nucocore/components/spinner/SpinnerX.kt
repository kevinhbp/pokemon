package id.co.app.nucocore.components.spinner

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.SpinnerAdapter

@SuppressLint("AppCompatCustomView")
class SpinnerX : Spinner {
  private var noClickSelectedPosition: Int = -1
  private var isAdapterLoaded: Boolean = false
  private var withDefAction: Boolean = true
  private var mOnItemSelectedListener: OnItemSelectedListener? = null

  constructor(context: Context?) : super(context) {
    setup()
  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    setup()
  }

  private fun setup() {
    this@SpinnerX.onItemSelectedListener = null
  }

  // -- OVERRIDE
  override fun setSelection(position: Int) {
    this.noClickSelectedPosition = -1
    super.setSelection(position)
  }

  override fun setSelection(position: Int, animate: Boolean) {
    this.noClickSelectedPosition = -1
    super.setSelection(position, animate)
  }

  override fun setAdapter(adapter: SpinnerAdapter?) {
    setAdapter(adapter, true)
  }

  override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
    this.mOnItemSelectedListener = listener
    super.setOnItemSelectedListener(mListener)
  }

  private val mListener: OnItemSelectedListener = object : OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
      mOnItemSelectedListener?.onNothingSelected(parent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      if (!isAdapterLoaded || !withDefAction || isAdapterLoaded && noClickSelectedPosition == position) {
        withDefAction = true
        noClickSelectedPosition = -1
        return
      }

      noClickSelectedPosition = -1
      mOnItemSelectedListener?.onItemSelected(parent, view, position, id)
    }
  }

  // --
  fun doSelection(position: Int, doAction: Boolean = false, doAnimation: Boolean = false) {
    if (!doAction) this.noClickSelectedPosition = position
    super.setSelection(position, doAnimation)
  }

  fun setAdapter(adapter: SpinnerAdapter?, doAction: Boolean) {
    withDefAction = doAction
    isAdapterLoaded = false
    super.setAdapter(adapter)
    isAdapterLoaded = true
  }

}