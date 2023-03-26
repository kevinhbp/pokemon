package id.co.app.nucocore.extension

import android.app.Activity
import android.content.Context
import com.tapadoo.alerter.Alerter
import es.dmoral.toasty.Toasty
import id.co.app.nucocore.R

@Suppress("unused")
fun Activity.showAlertError(
  title: String,
  message: String,
  durationInMs: Long
) {
  Alerter.create(this)
    .setTitle(title)
    .setText(message)
    .setBackgroundColorRes(R.color.nuco_red_3)
    .setDismissable(false)
    .setDuration(durationInMs)
    .show()
}