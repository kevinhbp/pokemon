@file:Suppress("unused")

package id.co.app.nucocore.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.co.app.nucocore.R
import id.co.app.nucocore.singleton.LOG_TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Float.dpToPx(): Float =
  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

fun Float.spToPx(): Float =
  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

// region Parallax Listener
@Suppress("unused")
fun RecyclerView.getParallaxListener(
  mMaxRange: Float = 200f,
  mSlowValue: Float = 2f,
  mListener: (translation: Float, alpha: Float) -> Unit
) {
  this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      val offset = recyclerView.computeVerticalScrollOffset().toFloat()
      val slow : Float = if (mSlowValue < 1) 1f else if (mSlowValue > 5) 5f else mSlowValue
      val maxOffset : Float = if (offset > mMaxRange) mMaxRange else offset
      val translation = (0f - (maxOffset / slow))
      val alpha = ((mMaxRange - maxOffset) / mMaxRange)
      mListener.invoke(translation, alpha)
      super.onScrolled(recyclerView, dx, dy)
    }
  })
}

@Suppress("unused")
fun NestedScrollView.getParallaxListener(
  mMaxRange: Float = 200f,
  mSlowValue: Float = 2f,
  mListener: (translation: Float, alpha: Float) -> Unit
) {
  this.viewTreeObserver.addOnScrollChangedListener {
    val mY = this.scrollY.toFloat()
    val maxOffset = if (mY > mMaxRange) mMaxRange else mY
    val translation = (0f - (maxOffset / mSlowValue))
    val alpha = ((mMaxRange - maxOffset) / mMaxRange)
    mListener.invoke(translation, alpha)
  }
}

@Suppress("unused")
fun ScrollView.getParallaxListener(
  mMaxRange: Float = 200f,
  mSlowValue: Float = 2f,
  mListener: (translation: Float, alpha: Float) -> Unit
) {
  this.viewTreeObserver.addOnScrollChangedListener {
    val mY = this.scrollY.toFloat()
    val maxOffset = if (mY > mMaxRange) mMaxRange else mY
    val translation = (0f - (maxOffset / mSlowValue))
    val alpha = ((mMaxRange - maxOffset) / mMaxRange)
    mListener.invoke(translation, alpha)
  }
}
// endregion

// region Status Bar
@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Activity.makeStatusBarTransparent() {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
  this.window.apply {
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
      decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    statusBarColor = Color.TRANSPARENT
  }
}

fun View.setMarginTop(marginTop: Int) {
  val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
  menuLayoutParams.setMargins(0, marginTop, 0, 0)
  this.layoutParams = menuLayoutParams
}

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Activity.makeStatusBarNotTransparent() {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
  this.window.apply {
    decorView.systemUiVisibility = 0
    statusBarColor = ContextCompat.getColor(this@makeStatusBarNotTransparent, R.color.primary_active)
  }
}
// endregion

// region Swipe Refresh Layout
fun SwipeRefreshLayout.delayedStopLoading(
  lifecycleScope: LifecycleCoroutineScope,
  mDelay: Long = 500L
) {
  val layout = this
  lifecycleScope.launch {
    delay(mDelay)
    layout.isRefreshing = false
  }
}

fun SwipeRefreshLayout.applyDefaultColor(): SwipeRefreshLayout {
  this.setColorSchemeResources(
    R.color.nuco_purple_1,
    R.color.nuco_red_2,
    R.color.nuco_light_blue_1,
  )
  return this
}
// endregion

fun RecyclerView.initItemVisibleListenerLinearLayout(
  listener: (first: Int, last: Int, progress: Int) -> Unit
) {
  if (this.layoutManager !is LinearLayoutManager) return
  val layMan = this.layoutManager as LinearLayoutManager
  this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(
      recyclerView: RecyclerView,
      dx: Int,
      dy: Int
    ) {
      val first = layMan.findFirstVisibleItemPosition()
      val last = layMan.findLastVisibleItemPosition()

      val range = recyclerView.computeVerticalScrollRange()
        .toFloat()
      val offset = recyclerView.computeVerticalScrollOffset()
        .toFloat()
      val extent = recyclerView.computeVerticalScrollExtent()
        .toFloat()
      val scrollProgress = (offset / (range - extent) * 100).toInt()

      if (last < 0) return
      listener.invoke(first, last, scrollProgress)
      super.onScrolled(recyclerView, dx, dy)
    }
  })
}