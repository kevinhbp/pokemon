package id.co.app.nucocore.binding

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import id.co.app.nucocore.R
import id.co.app.nucocore.singleton.LOG_TAG
import java.util.*

object ViewBinding {
  private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

  // This is the placeholder for the imageView
  private val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(
      Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
        .setBaseAlpha(0.9f) //the alpha of the underlying children
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()
    )
  }

  @JvmStatic
  @BindingAdapter("gone")
  fun bindGone(view: View, shouldBeGone: Boolean) {
    view.visibility = if (shouldBeGone) {
      View.GONE
    } else {
      View.VISIBLE
    }
  }

  @JvmStatic
  @BindingAdapter("titleText")
  fun bindTitleText(view: TextView, value: String?) {
    if (value == null) return
    view.text = value.capitalize(Locale.ROOT)
  }

  @JvmStatic
  @BindingAdapter("imageFromUrl")
  fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {

      Glide.with(view.context)
        .load(imageUrl)
        .placeholder(shimmerDrawable)
        .transition(withCrossFade(factory))
        .centerCrop()
        .into(view)
    }
  }

  @JvmStatic
  @BindingAdapter("imageFromDrawable")
  fun bindImageFromDrawable(view: ImageView, drawable: Drawable?) {
    if (drawable != null) {
      Glide.with(view.context)
        .load(drawable)
        .transition(withCrossFade())
        .centerCrop()
        .into(view)
    }
  }

  @JvmStatic
  @BindingAdapter("imageFromDrawableCenterInside")
  fun bindImageFromDrawableCenterInside(view: ImageView, drawable: Drawable?) {
    if (drawable != null) {
      Glide.with(view.context)
        .load(drawable)
        .transition(withCrossFade())
        .centerInside()
        .into(view)
    }
  }

  @JvmStatic
  @BindingAdapter("isFabGone")
  fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
      view.hide()
    } else {
      view.show()
    }
  }

  @JvmStatic
  @BindingAdapter("renderHtml")
  fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
      view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
      view.movementMethod = LinkMovementMethod.getInstance()
    } else {
      view.text = ""
    }
  }

  @JvmStatic
  @BindingAdapter("bindTextColor")
  fun bindTextColor(view: TextView, id: Int?) {
    if (id == null) return
    view.setTextColor(ContextCompat.getColor(view.context, id))
  }

  @JvmStatic
  @BindingAdapter("bindViewHeight")
  fun bindViewHeight(view: View, heightInPx: Int?) {
    if (heightInPx == null) return
    view.updateLayoutParams {
      height = heightInPx
    }
  }

  @JvmStatic
  @BindingAdapter("bindBackgroundTint")
  fun bindBackgroundTint(view: View, id: Int?) {
    if (id == null) return
    val context = view.context ?: return
    val defaultStateList = arrayOf(
      IntArray(1) { android.R.attr.state_enabled },
      IntArray(1) { -android.R.attr.state_enabled },
    )
    val colorList = IntArray(2).apply {
      this[0] = ContextCompat.getColor(context, id)
      this[1] = ContextCompat.getColor(context, id)
    }
    view.backgroundTintList = ColorStateList(defaultStateList, colorList)
  }

  @JvmStatic
  @BindingAdapter("bindImageResId")
  fun bindImageResId(view: ImageView, resId: Int?) {
    if (resId == null) return
    Glide.with(view.context)
      .load(resId)
      .transition(withCrossFade())
      .centerInside()
      .into(view)
  }

  @JvmStatic
  @BindingAdapter("bindImageData")
  fun bindImageData(view: ImageView, data: String?) {
    if (data.isNullOrEmpty()) {
      bindImageResId(view, R.drawable.illustration_image_placeholder)
      return
    }
    try {
      var mData = data.replaceFirst("data:image/jpeg;base64,", "")
      mData = mData.replaceFirst("data:image/png;base64,", "")
      Log.d(LOG_TAG, "imageData - $mData")
      if (mData.isEmpty()) {
        bindImageResId(view, R.drawable.illustration_image_placeholder)
        return
      }
      val dataImage = Base64.decode(mData, Base64.DEFAULT)
      val bmp = BitmapFactory.decodeByteArray(dataImage, 0, dataImage.size)
      Glide.with(view.context)
        .load(bmp)
        .placeholder(shimmerDrawable)
        .transition(withCrossFade())
        .error(R.drawable.illustration_image_placeholder)
        .centerInside()
        .into(view)
    } catch (e: Exception) {
      Log.e(LOG_TAG, e.localizedMessage.orEmpty())
    }
  }

  @JvmStatic
  @BindingAdapter("adaptHeightAsStatusBarSize")
  fun bindAdaptHeightAsStatusBarSize(view: View, flag: Boolean) {
    if (!flag) return
    val resources = view.context.resources
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val heightInPx = if (resId > 0) {
      resources.getDimensionPixelSize(resId)
    } else {
      Rect().apply {
        view.getWindowVisibleDisplayFrame(this)
      }.top
    }
    view.updateLayoutParams {
      height = heightInPx
    }
  }

  @JvmStatic
  @BindingAdapter("bottomNavigationAnimateHide")
  fun bindBottomNavigationAnimateHide(view: View, hide: Boolean) {
    view.clearAnimation()
    if (view.visibility == View.VISIBLE && !hide) return
    if (view.visibility == View.GONE && hide) return
    if (hide) {
      view.animate()
        .translationY(200f)
        .setDuration(200)
        .setInterpolator(LinearInterpolator())
        .withEndAction {
          view.animate().translationY(0f).setDuration(0).start()
          view.visibility = View.GONE
        }
        .start()
    } else {
      view.animate().translationY(200f).setDuration(0).start()
      view.animate()
        .translationY(0f)
        .setDuration(200)
        .setInterpolator(LinearInterpolator())
        .withStartAction {
          view.visibility = View.VISIBLE
        }
        .start()
    }

  }
}