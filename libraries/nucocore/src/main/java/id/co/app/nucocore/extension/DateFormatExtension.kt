package id.co.app.nucocore.extension

import android.util.Log
import id.co.app.nucocore.singleton.LOG_TAG
import java.text.SimpleDateFormat
import java.util.*

private const val SHORT_DATE = "dd/MM/yyyy"
private const val LONG_DATE = "dd/MM/yyyy HH:mm:ss"
private const val BUILD_TIME = "yyyyMMdd"
private const val SERVER_TIME_A = "yyyy-MM-dd"
private const val SERVER_TIME_B = "HH:mm:ss"

fun Long.toBuildTime(): String {
  val tz = TimeZone.getTimeZone("Asia/Jakarta")
  val formatter = SimpleDateFormat(BUILD_TIME, Locale.getDefault())
  val mTimeInMillis = this
  val calendar = Calendar.getInstance(tz).apply {
    timeInMillis = mTimeInMillis
  }
  return formatter.format(calendar.time)
}

fun Long.toShortDate(): String {
  val tz = TimeZone.getTimeZone("Asia/Jakarta")
  val formatter = SimpleDateFormat(SHORT_DATE, Locale.getDefault())
  val mTimeInMillis = this
  val calendar = Calendar.getInstance(tz).apply {
    timeInMillis = mTimeInMillis
  }
  return formatter.format(calendar.time)
}

fun Long.toLongDate(): String {
  val tz = TimeZone.getTimeZone("Asia/Jakarta")
  val formatter = SimpleDateFormat(LONG_DATE, Locale.getDefault())
  val mTimeInMillis = this
  val calendar = Calendar.getInstance(tz).apply {
    timeInMillis = mTimeInMillis
  }
  return formatter.format(calendar.time)
}

fun Long.toFormatDate(format: String = "dd MM yyyy"): String {
  val tz = TimeZone.getTimeZone("Asia/Jakarta")
  val formatter = SimpleDateFormat(format, Locale.getDefault())
  val mTimeInMillis = this
  val calendar = Calendar.getInstance(tz).apply {
    timeInMillis = mTimeInMillis
  }
  return formatter.format(calendar.time)
}

fun Long.toServerDate(): String = this.toFormatDate(SERVER_TIME_A) + "T" + this.toFormatDate(SERVER_TIME_B) + "Z"

fun String.toLong(format: String): Long? {
  val formatter = SimpleDateFormat(format, Locale.getDefault())
  return try {
    val d = formatter.parse(this)
    d!!.time
  } catch (e: Exception) {
    Log.e(LOG_TAG, e.message.orEmpty())
    null
  }
}

fun getCurrentYear(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toFormatDate("yyyy")
}

fun getCurrentMonthNumber(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toFormatDate("M")
}

fun getCurrentMonthShort(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toFormatDate("MMM")
}

fun getCurrentMonthLong(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toFormatDate("MMMM")
}

fun getCurrentDate(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toFormatDate("dd")
}

fun getCurrentShortDate(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toShortDate()
}

fun getCurrentLongDate(): String {
  val currentTime = System.currentTimeMillis()
  return currentTime.toLongDate()
}