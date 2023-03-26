package id.co.app.nucocore.domain.entities.row

import android.content.Context
import id.co.app.nucocore.R
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.domain.entities.view.ActionButtonModel
import java.io.Serializable

data class BlockerDefaultModel(
  var imageResId: Int,
  var textTitle: String,
  var textMessage: String,
  var buttonPrimary: ActionButtonModel?,
  var buttonSecondary: ActionButtonModel?,
) : Serializable {

  companion object {
    fun getNoBlock(): BlockerDefaultModel = BlockerDefaultModel(-1, "", "", null, null)

    fun Context.getInvalidConnectionBlocker(): BlockerDefaultModel = BlockerDefaultModel(
      R.drawable.illustration_connection_failure,
      this.getString(R.string.err_connection_label),
      this.getString(R.string.err_connection_message),
      ActionButtonModel(1, getString(R.string.re_connect), InternalDeepLink.RE_CONNECT),
      ActionButtonModel(2, getString(R.string.exit_application), InternalDeepLink.EXIT),
    )
  }
}