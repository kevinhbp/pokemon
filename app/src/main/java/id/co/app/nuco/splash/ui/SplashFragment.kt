package id.co.app.nuco.splash.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.core.animation.doOnEnd
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.co.app.nuco.databinding.FragmentSplashBinding
import id.co.app.nuco.splash.viewModels.SplashViewModel
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.R.dimen
import id.co.app.nucocore.R.drawable
import id.co.app.nucocore.extension.toDp
import id.co.app.nucocore.singleton.LOG_TAG
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

  private val splashViewModel: SplashViewModel by viewModel()
  private var menuShowing: Boolean = false
  private var animatingMenu: Boolean = false

  override fun provideBinding(): FragmentSplashBinding {
    return FragmentSplashBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    setStatusColor(true)
    setupMenuView()
  }

  override fun observeViewModel() {
    splashViewModel.splashModelLiveData.observe(viewLifecycleOwner) { model ->
      binding.model = model
    }
  }

  override fun loadData() {
    splashViewModel.start()
  }

  // --
  @SuppressLint("ClickableViewAccessibility")
  private fun setupMenuView() {
    val menuButton = binding.actionBarView.buttonMenu
    val shadowView = binding.shadowActionBar
    menuButton.setOnClickListener {
      showMenuContent(!menuShowing)
    }
    shadowView.setOnTouchListener { _, _ ->
      showMenuContent(!menuShowing)
      false
    }
  }

  private fun setMenuIcon(show: Boolean = true) {
    val menuIcon = binding.actionBarView.imageMenuIcon
    val menuIconDrawable = if (show) {
      drawable.ic_close
    } else {
      drawable.ic_menu
    }
    Glide.with(menuIcon.context)
      .load(menuIconDrawable)
      .transition(DrawableTransitionOptions.withCrossFade())
      .centerInside()
      .into(menuIcon)
  }

  private fun showMenuContent(show: Boolean = true) {
    setMenuIcon(show)
    val menuHeight = binding.root.resources.getDimension(dimen.menu_height)
    val shadowView = binding.shadowActionBar
    val menuView = binding.actionBarView.wrapperAbLv2
    if (menuShowing && show) return
    if (animatingMenu) return
    animatingMenu = true
    menuShowing = show
    if (show) {
      shadowView.visibility = View.VISIBLE
    }
    val valueAnimator = ValueAnimator.ofInt(0, 100)
    valueAnimator.duration = 200
    valueAnimator.addUpdateListener {
      val animatedValue = valueAnimator.animatedValue as Int
      val animatedPercentage = if (show) {
        animatedValue.toFloat() / 100f
      } else {
        (100f - animatedValue.toFloat()) / 100f
      }
      shadowView.alpha = animatedPercentage
      val animatedHeight = (menuHeight * animatedPercentage).toInt()
      val menuLayoutParams = menuView.layoutParams
      menuLayoutParams.height = animatedHeight
      menuView.layoutParams = menuLayoutParams
    }
    valueAnimator.doOnEnd {
      animatingMenu = false
      if (!show) {
        shadowView.visibility = View.GONE
      }
    }
    valueAnimator.start()
  }
}