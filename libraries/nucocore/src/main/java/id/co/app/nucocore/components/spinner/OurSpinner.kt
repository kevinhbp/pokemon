package id.co.app.nucocore.components.spinner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import id.co.app.nucocore.R
import id.co.app.nucocore.singleton.LOG_TAG

class OurSpinner(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), AdapterView.OnItemSelectedListener {

  private var label: String = ""

  private val mData: ArrayList<String> = arrayListOf()

  private var indexSelected: Int = 0

  private var mAdapter: ArrayAdapter<String>? = null

  private fun initWithAttributeSet(attrs: AttributeSet) {
    val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.OurSpinner)

    label = attributeArray.getString(R.styleable.OurSpinner_spinner_label) ?: ""

    attributeArray.recycle()
  }

  private var tvLabel: TextView

  private var spinnerView: SpinnerX

  private var onItemSelectedListener: ((position: Int, data: String) -> Unit)? = null

  init {
    View.inflate(context, R.layout.spinner_default, this)

    tvLabel = findViewById(R.id.tv_label)

    spinnerView = findViewById(R.id.vw_spinner)

    setupDefault()
    initWithAttributeSet(attrs)
    setupView()
    generateViewId()
  }

  @Suppress("unused")
  fun setSelected(index: Int) {
    val maxIndex = mData.size - 1
    if (index < 0 || index > maxIndex) return
    indexSelected = index
    setupView()
  }

  @Suppress("unused")
  fun getSelectedIndex(): Int {
    return indexSelected
  }

  // --
  private fun setupDefault() {
    tvLabel.text = ""
    tvLabel.visibility = View.GONE
    mData.clear()
    mAdapter = ArrayAdapter(context, R.layout.spinner_default_item, mData)
    spinnerView.adapter = mAdapter
    spinnerView.onItemSelectedListener = this
  }

  private fun setupView() {
    tvLabel.text = label
    tvLabel.visibility = if (label.isEmpty()) View.GONE else View.VISIBLE
    mAdapter = ArrayAdapter(context, R.layout.spinner_default_item, mData)
    spinnerView.adapter = mAdapter

    spinnerView.setSelection(indexSelected, false)
  }

  private fun generateViewId() {
    tvLabel.id = View.generateViewId()
    spinnerView.id = View.generateViewId()
  }

  // --
  @Suppress("unused")
  fun setData(data: ArrayList<String>) {
    mData.clear()
    mData.addAll(data)
    setupView()
  }

  @Suppress("unused")
  fun setOnItemSelected(listener: (position: Int, data: String) -> Unit) {
    this.onItemSelectedListener = listener
  }

  // --
  override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    onItemSelectedListener?.invoke(position, mData[position])
  }

  override fun onNothingSelected(parent: AdapterView<*>?) {
    onItemSelectedListener?.invoke(-1, "")
  }
}