package id.co.app.nucocore.extension

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Int.toPixel(context: Context): Int {
    val r: Resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        r.displayMetrics
    ).toInt()
}
fun Float.toPixel(context: Context): Int {
    val r: Resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        r.displayMetrics
    ).toInt()
}
fun String?.toIntOrZero(): Int = (this?.replace(".", "")?.toIntOrNull() ?: 0)
fun String?.toLongOrZero(): Long = (this?.replace(".", "")?.toLongOrNull() ?: 0L)
fun Long.toThousandFormat(): String = NumberFormat.getNumberInstance(Locale.US)
    .format(this)
    .replace(",", ".")
fun Long?.withDefault(): Long = this ?: 0L
fun Int.toThousandFormat(): String = NumberFormat.getNumberInstance(Locale.US)
    .format(this)
    .replace(",", ".")

fun Int.toTonaseFormat(): String = this.toDouble().div(1000).round2DecimalPlaceString()
fun Long.toTonaseFormat(): String = this.toDouble().div(1000).round2DecimalPlaceString()
fun Double?.toTonaseFormat(): String = this?.div(1000.0)?.round2DecimalPlaceString() ?: "0.0"
fun Double.toThousandFormat(): String = NumberFormat.getNumberInstance(Locale.US)
    .format(this.toInt())
    .replace(",", ".")

fun Double?.toThousandFormat(): String = NumberFormat.getNumberInstance(Locale.US)
    .format((this ?: 0.0).toInt())
    .replace(",", ".")

fun Int.maxPercentage(): Int = if (this > 100) 100 else this

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).replace(",", ".").toDouble()
}

fun Double.round2DecimalPlace(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).replace(",", ".").toDouble()
}

fun Double.round1DecimalPlace(): Double {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).replace(",", ".").toDouble()
}

fun Double.round2DecimalPlaceString(): String {
    return if (this == 0.0) "0"
    else {
        val formatDecimal = DecimalFormat("0.##")
        formatDecimal.roundingMode = RoundingMode.DOWN
        formatDecimal.format(this)
    }
}