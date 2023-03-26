package id.co.app.nucocore.extension

import android.content.Context
import es.dmoral.toasty.Toasty

@Suppress("unused")
fun Context.showToastError(
  message: String,
  duration: Int
) {
  Toasty.error(this, message, duration, true).show()
}

@Suppress("unused")
fun Context.showToastSuccess(
  message: String,
  duration: Int
) {
  Toasty.success(this, message, duration, true).show()
}

@Suppress("unused")
fun Context.showToastInfo(
  message: String,
  duration: Int
) {
  Toasty.info(this, message, duration, true).show()
}

@Suppress("unused")
fun Context.showToastWarning(
  message: String,
  duration: Int
) {
  Toasty.warning(this, message, duration, true).show()
}

@Suppress("unused")
fun Context.showToastNormal(
  message: String,
  duration: Int
) {
  Toasty.normal(this, message, duration).show()
}