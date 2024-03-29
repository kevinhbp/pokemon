package id.co.app.nucocore.singleton

import androidx.navigation.NavOptions
import id.co.app.nucocore.R

class ViewSingleton {

  val navOptionsLv0: NavOptions by lazy {
    NavOptions.Builder()
      .setEnterAnim(R.anim.slide_in_bottom)
      .setExitAnim(R.anim.slide_out_top)
      .setPopEnterAnim(R.anim.slide_in_top)
      .setPopExitAnim(R.anim.slide_out_bottom)
      .build()
  }

  val navOptionsLv1: NavOptions by lazy {
    NavOptions.Builder()
      .setEnterAnim(R.anim.slide_in_right)
      .setExitAnim(R.anim.slide_out_left)
      .setPopEnterAnim(R.anim.slide_in_left)
      .setPopExitAnim(R.anim.slide_out_right)
      .build()
  }

  companion object {
    private lateinit var instance: ViewSingleton

    fun getDefaultInstance(): ViewSingleton {
      if (!::instance.isInitialized) {
        instance = ViewSingleton()
      }
      return instance
    }
  }
}

const val LOG_TAG = "appSource"