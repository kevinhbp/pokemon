package id.co.app.nucocore.extension

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import id.co.app.nucocore.typeface.Font
import id.co.app.nucocore.utilities.PieChartFormatter

fun PieChart.applyDefaultSettings(withLegend: Boolean = false): PieChart {
  val mTypeface = Font.getBold(this.context)
  this.apply {
    description.isEnabled = false

    if (withLegend) {
      setExtraOffsets(0f, 5f, 25f, 5f)
    } else {
      setExtraOffsets(2f, 2f, 2f, 2f)
    }

    dragDecelerationFrictionCoef = 0.95f
    setDrawCenterText(false)
    centerText = ""
    isDrawHoleEnabled = true
    setHoleColor(Color.TRANSPARENT)

    setTransparentCircleColor(Color.WHITE)
    setTransparentCircleAlpha(0)

    holeRadius = 46f
    transparentCircleRadius = (holeRadius + 3f)
    rotationAngle = 135f
    isRotationEnabled = true
    isHighlightPerTapEnabled = true
    animateY(1200, Easing.EaseInOutQuad)

    setEntryLabelColor(Color.WHITE)
    setEntryLabelTypeface(mTypeface)
    setEntryLabelTextSize(12f)

    legend.isEnabled = withLegend
    setDrawEntryLabels(false)
    setDrawRoundedSlices(false)

    if (withLegend) {
      legend.apply {
        verticalAlignment = Legend.LegendVerticalAlignment.TOP
        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        orientation = Legend.LegendOrientation.VERTICAL
        setDrawInside(false)
        xEntrySpace = 7f
        yEntrySpace = 0f
        yOffset = 0f
        typeface = mTypeface
      }
    }

    description = null
  }
  return this
}

fun PieChart.reAnimate() {
  this.animateY(1200, Easing.EaseInOutQuad)
}

fun ArrayList<PieEntry>.toDataSet(mLabel: String, mColors: ArrayList<Int>): PieDataSet = PieDataSet(this, mLabel).apply {
  setDrawIcons(false)
  // sliceSpace = 4f
  iconsOffset = MPPointF(0f, 40f)
  selectionShift = 5f
  colors = mColors
}

fun PieDataSet.toPieData(context: Context): PieData = PieData(this).apply {
  setValueFormatter(PieChartFormatter())
  setValueTextSize(12f)
  setValueTextColor(Color.WHITE)
  setValueTypeface(Font.getDemi(context))
  setDrawValues(false)
}