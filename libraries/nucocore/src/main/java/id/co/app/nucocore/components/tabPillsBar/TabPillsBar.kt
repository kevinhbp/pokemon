package id.co.app.nucocore.components.tabPillsBar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import id.co.app.nucocore.R

class TabPillsBar(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

  private var label1: String = ""

  private var label2: String = ""

  private var label3: String = ""

  private var labelSelected: String = ""

  private var labelAllCaps: Boolean = false

  private var indexSelected: Int = 0

  private var buttonListener: ((selectedIndex: Int) -> Unit)? = null

  private val colorBackgroundDefault: Int = ContextCompat.getColor(context, R.color.color_tabs_wrapper)

  private var colorBackground: Int = colorBackgroundDefault

  private val colorTabDefault: Int = ContextCompat.getColor(context, R.color.color_tabs)

  private var colorTab: Int = colorTabDefault

  private val colorTextDefaultDefault: Int = ContextCompat.getColor(context, R.color.color_on_tabs_default)

  private var colorTextDefault: Int = colorTextDefaultDefault

  private val colorTextActiveDefault: Int = ContextCompat.getColor(context, R.color.color_on_tabs_active)

  private var colorTextActive: Int = colorTextActiveDefault

  private fun initWithAttributeSet(attrs: AttributeSet) {
    val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.TabPillsBar)

    // Selected Index
    indexSelected = attributeArray.getInteger(R.styleable.TabPillsBar_tab_selectedIndex, 0)

    labelAllCaps = attributeArray.getBoolean(R.styleable.TabPillsBar_tab_labelAllCaps, false)

    // Labeling
    label1 = attributeArray.getString(R.styleable.TabPillsBar_tab_label1) ?: ""

    label2 = attributeArray.getString(R.styleable.TabPillsBar_tab_label2) ?: ""

    label3 = attributeArray.getString(R.styleable.TabPillsBar_tab_label3) ?: ""

    if (labelAllCaps) {
      label1 = label1.uppercase()
      label2 = label2.uppercase()
      label3 = label3.uppercase()
    }

    labelSelected = when (indexSelected) {
      0 -> label1
      1 -> label2
      else -> label3
    }

    // Background Color
    colorBackground = attributeArray.getColor(R.styleable.TabPillsBar_tab_backgroundColor, colorBackgroundDefault)

    // Tabs Color
    colorTab = attributeArray.getColor(R.styleable.TabPillsBar_tab_tabColor, colorTabDefault)

    // Text Color
    colorTextDefault = attributeArray.getColor(R.styleable.TabPillsBar_tab_textColor, colorTextDefaultDefault)

    // Selected Text Color
    colorTextActive = attributeArray.getColor(R.styleable.TabPillsBar_tab_activeTextColor, colorTextActiveDefault)

    attributeArray.recycle()
  }

  // --
  private var cvWrapper: CardView

  private var cvTab: CardView

  private var tvLabel1: TextView

  private var tvLabel2: TextView

  private var tvLabel3: TextView

  private var tvLabelSelected: TextView

  private var vwSpaceLeft1: View

  private var vwSpaceLeft2: View

  private var vwSpaceRight1: View

  private var vwSpaceRight2: View

  init {
    View.inflate(context, R.layout.tab_pills_bar, this)

    cvWrapper = findViewById(R.id.tab_pills_bar_cv_pills)

    cvTab = findViewById(R.id.tab_pills_bar_cv_selected)

    tvLabel1 = findViewById(R.id.tab_pills_bar_text_tab_1)

    tvLabel2 = findViewById(R.id.tab_pills_bar_text_tab_2)

    tvLabel3 = findViewById(R.id.tab_pills_bar_text_tab_3)

    tvLabelSelected = findViewById(R.id.tab_pills_bar_text_tab_selected)

    vwSpaceLeft1 = findViewById(R.id.tab_pills_bar_space_1)

    vwSpaceLeft2 = findViewById(R.id.tab_pills_bar_space_2)

    vwSpaceRight1 = findViewById(R.id.tab_pills_bar_space_3)

    vwSpaceRight2 = findViewById(R.id.tab_pills_bar_space_4)

    setupDefault()
    initWithAttributeSet(attrs)
    setupView()
    generateViewId()
  }

  @Suppress("unused")
  fun setSelected(index: Int) {
    if (index < 0 || index > 2) return
    indexSelected = index
    setupView()
  }

  @Suppress("unused")
  fun getSelectedIndex(): Int {
    return indexSelected
  }

  fun setOnButtonClickedListener(listener: (selectedIndex: Int) -> Unit) {
    buttonListener = listener
  }

  // --
  private fun setupDefault() {
    cvWrapper.setCardBackgroundColor(colorBackgroundDefault)
    cvTab.setCardBackgroundColor(colorTabDefault)

    tvLabel1.apply {
      setTextColor(colorTextDefaultDefault)
      text = ""
    }
    tvLabel2.apply {
      setTextColor(colorTextDefaultDefault)
      text = ""
    }
    tvLabel3.apply {
      setTextColor(colorTextDefaultDefault)
      text = ""
    }
    tvLabelSelected.apply {
      setTextColor(colorTextActiveDefault)
      text = ""
    }

    vwSpaceLeft1.apply {
      visibility = View.GONE
    }
    vwSpaceLeft2.apply {
      visibility = View.GONE
    }

    vwSpaceRight1.apply {
      visibility = View.VISIBLE
    }
    vwSpaceRight2.apply {
      visibility = View.VISIBLE
    }

    tvLabel1.setOnClickListener {
      indexSelected = 0
      onIndexChange()
    }
    tvLabel2.setOnClickListener {
      indexSelected = 1
      onIndexChange()
    }
    tvLabel3.setOnClickListener {
      indexSelected = 2
      onIndexChange()
    }
  }

  private fun setupView() {
    cvWrapper.setCardBackgroundColor(colorBackground)
    cvTab.setCardBackgroundColor(colorTab)

    tvLabel1.apply {
      setTextColor(colorTextDefault)
      text = label1
    }
    tvLabel2.apply {
      setTextColor(colorTextDefault)
      text = label2
    }
    tvLabel3.apply {
      setTextColor(colorTextDefault)
      text = label3
    }
    tvLabelSelected.apply {
      setTextColor(colorTextActive)
      text = labelSelected
    }

    vwSpaceLeft1.apply {
      visibility = if (indexSelected == 0) View.GONE else View.VISIBLE
    }
    vwSpaceLeft2.apply {
      visibility = if (indexSelected == 2) View.VISIBLE else View.GONE
    }

    vwSpaceRight1.apply {
      visibility = if (indexSelected == 0) View.VISIBLE else View.GONE
    }
    vwSpaceRight2.apply {
      visibility = if (indexSelected == 2) View.GONE else View.VISIBLE
    }
  }

  private fun generateViewId() {
    tvLabel1.id = View.generateViewId()
    tvLabel2.id = View.generateViewId()
    tvLabel3.id = View.generateViewId()
    tvLabelSelected.id = View.generateViewId()
  }

  private fun onIndexChange() {
    if (indexSelected == 0) {
      tvLabelSelected.text = label1
      showSL1(false)
      showSL2(false)
      showSR1(true)
      showSR2(true)
    }
    if (indexSelected == 1) {
      tvLabelSelected.text = label2
      showSL1(true)
      showSL2(false)
      showSR1(false)
      showSR2(true)
    }
    if (indexSelected == 2) {
      tvLabelSelected.text = label3
      showSL1(true)
      showSL2(true)
      showSR1(false)
      showSR2(false)
    }
    buttonListener?.invoke(indexSelected)
  }

  private fun showSL1(show: Boolean) {
    vwSpaceLeft1.visibility = if (show) View.VISIBLE else View.GONE
  }

  private fun showSL2(show: Boolean) {
    vwSpaceLeft2.visibility = if (show) View.VISIBLE else View.GONE
  }


  private fun showSR1(show: Boolean) {
    vwSpaceRight1.visibility = if (show) View.VISIBLE else View.GONE
  }

  private fun showSR2(show: Boolean) {
    vwSpaceRight2.visibility = if (show) View.VISIBLE else View.GONE
  }
}