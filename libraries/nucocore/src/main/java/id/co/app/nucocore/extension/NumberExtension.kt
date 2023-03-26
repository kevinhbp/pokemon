package id.co.app.nucocore.extension

import id.co.app.nucocore.extension.withDefault
import java.text.NumberFormat
import java.util.*

fun Double?.toThousandFormat(rounding: Boolean = false): String = NumberFormat.getNumberInstance(Locale.US)
  .format(if (rounding) (this ?: 0.0).toInt() else (this ?: 0.0))
  .replace(".", "/").replace(",", ".")
  .replace("/", ",")