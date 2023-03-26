package id.co.app.nucocore.components.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import id.co.app.nucocore.R.*

@Suppress("unused")
fun getDialogInstance(): Dialog {
  return Dialog.get()
}

@Suppress("unused")
fun Activity.showMessageDialog(
  title: String,
  message: String,
  onDismiss: (() -> Unit)?
) {
  val mDialog = Dialog.get()
  mDialog.showMessage(this, title, message, onDismiss)
}

@Suppress("unused")
fun Activity.showConfirmationDialog(
  title: String,
  message: String,
  positiveButton: String,
  negativeButton: String,
  onConfirmed: (isPositive: Boolean) -> Unit
) {
  val mDialog = Dialog.get()
  mDialog.showConfirmation(this, title, message, positiveButton, negativeButton, onConfirmed)
}

@Suppress("unused")
fun Context.showToastMessage(
  message: String,
  doLong: Boolean
) {
  val mDialog = Dialog.get()
  mDialog.showToast(this, message, doLong)
}

@Suppress("unused")
fun Activity.showLoadingDialog(
  show: Boolean
) {
  val mDialog = Dialog.get()
  mDialog.showLoading(this, show)
}

class Dialog : DefaultLifecycleObserver {

  private var mToast: Toast? = null

  private var mMessageDialog: MaterialDialog? = null

  private var mLoadingDialog: MaterialDialog? = null

  private var mPaused: Boolean = false

  // --
  @SuppressLint("InflateParams")
  fun showMessage(mActivity: Activity, title: String, message: String, onDismiss: (() -> Unit)?) {
    if (title.isEmpty() && message.isEmpty()) return
    if (mPaused) return
    val mView = mActivity.layoutInflater.inflate(layout.dialog_default, null, false)
    mView.getView<View>(id.vw_spc).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_negative).visibility = View.GONE
    mView.getView<TextView>(id.btn_positive).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_positive).text = mActivity.getString(string.ok)
    mView.getView<TextView>(id.tv_title).text = title
    mView.getView<TextView>(id.tv_body).text = message
    mView.getView<TextView>(id.tv_title).visibility =
      if (title.isEmpty()) View.GONE else View.VISIBLE
    mView.getView<TextView>(id.btn_positive).setOnClickListener {
      mMessageDialog?.dismiss()
      onDismiss?.invoke()
    }
    mView.getView<TextView>(id.btn_negative).setOnClickListener {
      mMessageDialog?.dismiss()
      onDismiss?.invoke()
    }
    mMessageDialog?.dismiss()
    mMessageDialog = MaterialDialog(mActivity)
      .customView(view = mView, dialogWrapContent = false, noVerticalPadding = true)
      .cancelable(false)
      .cancelOnTouchOutside(false)
      .cornerRadius(res = dimen.radius_card_small)
    mMessageDialog?.setOnCancelListener {
      onDismiss?.invoke()
    }
    try {
      mMessageDialog?.show()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @SuppressLint("InflateParams")
  fun showConfirmation(
    mActivity: Activity,
    title: String,
    message: String,
    positiveButton: String,
    negativeButton: String,
    onConfirmed: (isPositive: Boolean) -> Unit
  ) {
    if (title.isEmpty() && message.isEmpty()) return
    if (positiveButton.isEmpty() && negativeButton.isEmpty()) return
    if (mPaused) return
    val mView = mActivity.layoutInflater.inflate(layout.dialog_default, null, false)
    mView.getView<View>(id.vw_spc).visibility = View.GONE
    mView.getView<TextView>(id.btn_negative).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_positive).visibility = View.VISIBLE
    mView.getView<TextView>(id.btn_negative).text = negativeButton
    mView.getView<TextView>(id.btn_positive).text = positiveButton
    mView.getView<TextView>(id.tv_title).text = title
    mView.getView<TextView>(id.tv_body).text = message
    mView.getView<TextView>(id.tv_title).visibility =
      if (title.isEmpty()) View.GONE else View.VISIBLE
    mView.getView<TextView>(id.btn_positive).setOnClickListener {
      mMessageDialog?.dismiss()
      onConfirmed.invoke(true)
    }
    mView.getView<TextView>(id.btn_negative).setOnClickListener {
      mMessageDialog?.dismiss()
      onConfirmed.invoke(false)
    }
    mMessageDialog?.dismiss()
    mMessageDialog = MaterialDialog(mActivity)
      .customView(view = mView, dialogWrapContent = false, noVerticalPadding = true)
      .cancelable(false)
      .cancelOnTouchOutside(false)
      .cornerRadius(res = dimen.radius_card_small)
    try {
      mMessageDialog?.show()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @SuppressLint("InflateParams")
  fun showLoading(mActivity: Activity, show: Boolean) {
    if (mPaused) return
    if (mLoadingDialog?.isShowing == true) mLoadingDialog?.dismiss()
    if (!show) return
    if (mLoadingDialog == null) {
      val mView = mActivity.layoutInflater.inflate(layout.dialog_loading, null, false)
      mView.getView<TextView>(id.text_body).text = mActivity.getString(string.please_wait)
      mLoadingDialog = MaterialDialog(mActivity)
        .customView(view = mView, dialogWrapContent = false, noVerticalPadding = true)
        .cancelable(false)
        .cancelOnTouchOutside(false)
        .cornerRadius(res = dimen.radius_card_small)
    }
    try {
      if (!mLoadingDialog?.isShowing!!) {
        mLoadingDialog?.show()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun showToast(context: Context, message: String, doLong: Boolean) {
    if (mPaused) return
    val mLength = if (doLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    mToast?.cancel()
    mToast = Toast.makeText(context, message, mLength)
    mToast?.setGravity(Gravity.CENTER, 0, 0)
    try {
      mToast?.show()
    } catch (e: Exception) {
      Log.e(LOG_TAG, "Cannot show toast - $e")
    }
  }

  override fun onPause(owner: LifecycleOwner) {
    super.onPause(owner)
    // will block dialog request when activity is on paused
    mPaused = true
    mToast?.cancel()
  }

  override fun onResume(owner: LifecycleOwner) {
    super.onResume(owner)
    // cancel block flag when activity is on resume
    mPaused = false
  }

  // --
  private fun <T : View> View.getView(id: Int): T {
    return this.findViewById(id)
  }

  companion object {
    private const val LOG_TAG = "kputro"
    private lateinit var dialogInstance: Dialog

    fun get(): Dialog {
      if (!::dialogInstance.isInitialized) {
        dialogInstance = Dialog()
        return dialogInstance
      }

      return dialogInstance
    }
  }
}