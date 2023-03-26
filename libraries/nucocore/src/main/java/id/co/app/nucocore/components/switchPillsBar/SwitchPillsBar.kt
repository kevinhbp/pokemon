package id.co.app.nucocore.components.switchPillsBar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import id.co.app.nucocore.R

class SwitchPillsBar(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

  private var label1: String = ""

  private var label2: String = ""

  private var labelSelected: String = ""

  private var labelAllCaps: Boolean = false

  private var image1: Drawable? = null

  private var image2: Drawable? = null

  private var imageSelected: Drawable? = null

  private var indexSelected: Int = 0

  private var onSelectedIndexChanged: ((selectedIndex: Int) -> Unit)? = null

  private val colorBackgroundDefault: Int = ContextCompat.getColor(context, R.color.color_switch_wrapper)

  private var colorBackground: Int = colorBackgroundDefault

  private val colorTabDefault: Int = ContextCompat.getColor(context, R.color.color_switch)

  private var colorTab: Int = colorTabDefault

  private val colorTextDefaultDefault: Int = ContextCompat.getColor(context, R.color.color_on_switch_default)

  private var colorTextDefault: Int = colorTextDefaultDefault

  private val colorTextActiveDefault: Int = ContextCompat.getColor(context, R.color.color_on_switch_active)

  private var colorTextActive: Int = colorTextActiveDefault

  private fun initWithAttributeSet(attrs: AttributeSet) {
    val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchPillsBar)

    // Selected Index
    indexSelected = attributeArray.getInteger(R.styleable.SwitchPillsBar_switch_selectedIndex, 0)

    labelAllCaps = attributeArray.getBoolean(R.styleable.SwitchPillsBar_switch_labelAllCaps, false)

    // Labeling
    label1 = attributeArray.getString(R.styleable.SwitchPillsBar_switch_label1) ?: ""

    label2 = attributeArray.getString(R.styleable.SwitchPillsBar_switch_label2) ?: ""

    // Icon Image
    image1 = attributeArray.getDrawable(R.styleable.SwitchPillsBar_switch_image1)

    image2 = attributeArray.getDrawable(R.styleable.SwitchPillsBar_switch_image2)

    if (labelAllCaps) {
      label1 = label1.uppercase()
      label2 = label2.uppercase()
    }

    labelSelected = if (indexSelected < 1) {
      label1
    } else {
      label2
    }

    imageSelected = if (indexSelected < 1) {
      image1
    } else {
      image2
    }

    // Background Color
    colorBackground = attributeArray.getColor(R.styleable.SwitchPillsBar_switch_backgroundColor, colorBackgroundDefault)

    // Tabs Color
    colorTab = attributeArray.getColor(R.styleable.SwitchPillsBar_switch_pillsColor, colorTabDefault)

    // Text Color
    colorTextDefault = attributeArray.getColor(R.styleable.SwitchPillsBar_switch_textColor, colorTextDefaultDefault)

    // Selected Text Color
    colorTextActive = attributeArray.getColor(R.styleable.SwitchPillsBar_switch_activeTextColor, colorTextActiveDefault)

    attributeArray.recycle()
  }

  // --
  private var cvWrapper: CardView

  private var cvTab: CardView

  private var tvLabel1: TextView

  private var tvLabel2: TextView

  private var tvLabelSelected: TextView

  private var ivImage1: ImageView

  private var ivImage2: ImageView

  private var ivImageSelected: ImageView

  private var vwSpace1: View

  private var vwSpace2: View

  private var btnPills1: View

  private var btnPills2: View

  init {
    View.inflate(context, R.layout.switch_pills_bar, this)

    cvWrapper = findViewById(R.id.switch_pills_bar_cv_pills)

    cvTab = findViewById(R.id.switch_pills_bar_cv_selected)

    tvLabel1 = findViewById(R.id.text_pills_1)

    tvLabel2 = findViewById(R.id.text_pills_2)

    tvLabelSelected = findViewById(R.id.text_pills_selected)

    ivImage1 = findViewById(R.id.image_pills_1)

    ivImage2 = findViewById(R.id.image_pills_2)

    ivImageSelected = findViewById(R.id.image_pills_selected)

    vwSpace1 = findViewById(R.id.space_pills_1)

    vwSpace2 = findViewById(R.id.space_pills_2)

    btnPills1 = findViewById(R.id.button_pills_1)

    btnPills2 = findViewById(R.id.button_pills_2)

    setupDefault()
    initWithAttributeSet(attrs)
    setupView()
    generateViewId()
  }

  fun setLabel1(text: String) {
    label1 = text
    setupView()
  }

  fun setLabel2(text: String) {
    label2 = text
    setupView()
  }

  @Suppress("unused")
  fun setSelected(index: Int) {
    if (index < 0 || index > 1) return
    indexSelected = index
    refreshView()
  }

  @Suppress("unused")
  fun getSelectedIndex(): Int {
    return indexSelected
  }

  fun setOnSelectedIndexChangedListener(listener: (selectedIndex: Int) -> Unit) {
    onSelectedIndexChanged = listener
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
    tvLabelSelected.apply {
      setTextColor(colorTextActiveDefault)
      text = ""
    }

    ivImage1.apply {
      visibility = View.GONE
    }

    ivImage2.apply {
      visibility = View.GONE
    }

    ivImageSelected.apply {
      visibility = View.GONE
    }

    vwSpace1.apply {
      visibility = View.GONE
    }
    vwSpace2.apply {
      visibility = View.GONE
    }

    btnPills1.setOnClickListener {
      indexSelected = 0
      onIndexChange()
    }
    btnPills2.setOnClickListener {
      indexSelected = 1
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

    ivImage1.apply {
      if (image1 != null) {
        visibility = View.VISIBLE
        setImageDrawable(image1)
      } else {
        visibility = View.GONE
      }
    }
    ivImage2.apply {
      if (image2 != null) {
        visibility = View.VISIBLE
        setImageDrawable(image2)
      } else {
        visibility = View.GONE
      }
    }

    refreshView()
  }

  private fun refreshView() {
    if (indexSelected == 0) {
      labelSelected = label1
      imageSelected = image1
    } else if (indexSelected == 1) {
      labelSelected = label2
      imageSelected = image2
    }

    tvLabelSelected.apply {
      setTextColor(colorTextActive)
      text = labelSelected
    }
    ivImageSelected.apply {
      if (imageSelected != null) {
        visibility = View.VISIBLE
        setImageDrawable(imageSelected)
      } else {
        visibility = View.GONE
      }
    }

    if (indexSelected == 0) {
      vwSpace1.visibility = View.GONE
      vwSpace2.visibility = View.VISIBLE
    } else {
      vwSpace1.visibility = View.VISIBLE
      vwSpace2.visibility = View.GONE
    }
  }

  private fun generateViewId() {
    tvLabel1.id = View.generateViewId()
    tvLabel2.id = View.generateViewId()
    tvLabelSelected.id = View.generateViewId()
  }

  private fun onIndexChange() {
    refreshView()
    onSelectedIndexChanged?.invoke(indexSelected)
  }
}