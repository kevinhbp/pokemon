package id.co.app.nucocore.utilities

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

open class PieChartFormatter() : ValueFormatter() {

  val mFormat: DecimalFormat = DecimalFormat("#0.0")
  private var pieChart: PieChart? = null

  constructor(pieChart: PieChart) : this() {
    this.pieChart = pieChart
  }

  override fun getFormattedValue(value: Float): String {
    return mFormat.format(value)
  }

  override fun getPieLabel(value: Float, pieEntry: PieEntry): String {
    val entry = mFormat.format(pieEntry.value)
    val percentage = getFormattedValue(value) + "%"
    return "$entry ($percentage)"
  }
}