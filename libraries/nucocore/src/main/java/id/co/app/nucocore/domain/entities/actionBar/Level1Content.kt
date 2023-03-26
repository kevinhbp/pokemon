package id.co.app.nucocore.domain.entities.actionBar

import android.content.Context
import id.co.app.nucocore.R
import java.io.Serializable

data class Level1Content(
  val title: String,
  val subtitle: String,
  var iconDrawableResId: Int?,
  var circleColor: Int?
) : Serializable {


  companion object {
    fun Context.getPokedex() : Level1Content {
      val mTitle = this.getString(R.string.pokedex)
      val mSubtitle = this.getString(R.string.check_pokedex)
      val mColor = R.color.nuco_orange_0
      return Level1Content(mTitle, mSubtitle, null, mColor)
    }
  }
}