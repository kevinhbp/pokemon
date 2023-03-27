package id.co.app.nuco.main.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.co.app.nuco.R.id
import id.co.app.nuco.R.string
import id.co.app.nuco.databinding.ActivityMainBinding
import id.co.app.nuco.main.viewModels.MainState
import id.co.app.nuco.main.viewModels.MainViewModel
import id.co.app.nucocore.R
import id.co.app.nucocore.components.blocker.BlockerViewModel
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel
import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel.Companion.getInvalidConnectionBlocker
import id.co.app.nucocore.extension.makeStatusBarTransparent
import id.co.app.nucocore.extension.showToastInfo
import id.co.app.nucocore.extension.showToastNormal
import id.co.app.nucocore.navigation.MainActNavi
import id.co.app.nucocore.singleton.LOG_TAG
import id.co.app.nucocore.singleton.ViewSingleton
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainActNavi {
  private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  private var doubleBackToExitPressedOnce = false
  private val rollbackPressedOnce = Runnable { doubleBackToExitPressedOnce = false }

  private val viewModel: MainViewModel by viewModel()
  private val blockerViewModel: BlockerViewModel by viewModel()

  private var menuShowing: Boolean = false
  private var animatingMenu: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    makeStatusBarTransparent()
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    setupView()
    observeViewModel()
  }

  // --
  override fun onBackPressed() {
    if (onPreventExitPage()) {
      return
    }
    if (onOverrideBackPressedPage()) {
      super.onBackPressed()
      return
    }
    if (onDefaultPage()) {
      if (onDestinationDefault() && !doubleBackToExitPressedOnce) {
        doubleBackToExitPressedOnce = true
        showToastNormal(getString(string.exit_confirm), Toast.LENGTH_LONG)
        Handler(Looper.getMainLooper()).postDelayed(rollbackPressedOnce, 2000)
        return
      }
      if (onDestinationDefault() && doubleBackToExitPressedOnce) {
        finishAffinity()
        return
      }
      return
    } else {
      navigateUp()
    }
  }

  private fun getNavController(): NavController {
    return findNavController(id.nav_host_fragment)
  }

  private fun onDefaultPage(): Boolean {
    val nav = getNavController()
    val noBackPreviousStackEntry = nav.previousBackStackEntry == null
    val noCurrentBackStackEntry = nav.currentBackStackEntry == null
    return (noBackPreviousStackEntry || noCurrentBackStackEntry || onDestinationDefault())
  }

  private fun onDestinationDefault(): Boolean {
    val nav = getNavController()
    val preventSingleTouchExitFragments = arrayListOf<Int>().apply {
      add(id.splash_fragment)
      add(id.home_fragment)
    }
    val destinationId = nav.currentBackStackEntry?.destination?.id ?: -1
    return preventSingleTouchExitFragments.contains(destinationId)
  }

  private fun onPreventExitPage(): Boolean {
    val nav = getNavController()
    val fragmentList = listOf<Int>()
    val destinationId = nav.currentBackStackEntry?.destination?.id ?: -1
    return fragmentList.contains(destinationId)
  }

  private fun onOverrideBackPressedPage(): Boolean {
    val nav = getNavController()
    val fragmentList = listOf<Int>()
    val destinationId = nav.currentBackStackEntry?.destination?.id ?: -1
    return fragmentList.contains(destinationId)
  }

  private fun navigateUp(): Boolean {
    val nav = getNavController()
    val previousBackStack = nav.previousBackStackEntry?.destination?.label ?: ""
    if (previousBackStack.isEmpty()) return false
    return nav.navigateUp()
  }

  // --
  private fun setupView() {
    binding.blockerViewModel = blockerViewModel
    binding.executePendingBindings()

    setupMenuView()
  }

  private fun observeViewModel() {
    viewModel.mainStateLiveData.observe(this) { state ->
      when (state) {
        MainState.SHOW_BLOCKER_CONNECTION -> {
          showBlockerView(getInvalidConnectionBlocker())
        }
        MainState.REDIRECT_HOME -> {

        }
        else -> { /* not used at the moment.. */
        }
      }
    }

    blockerViewModel.targetLiveData.observe(this) {
      Log.d(LOG_TAG, "Blocker Target: $it")
      onTargetChanged(it)
    }
  }

  // --
  override fun showActionBar(show: Boolean) {
    val view = binding.actionBarView.abDefault
    view.visibility = if (show) View.VISIBLE else View.GONE
  }

  override fun showBlockerView(model: BlockerDefaultModel) {
    blockerViewModel.showWithModel(model)
  }

  private fun onTargetChanged(it: String) {
    if (it == InternalDeepLink.EXIT) {
      finishAffinity()
      return
    }

    try {
      getNavController().navigate(it.toUri(), ViewSingleton.getDefaultInstance().navOptions)
    } catch (e: Exception) {
      e.printStackTrace()
    }
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
      R.drawable.ic_close
    } else {
      R.drawable.ic_menu
    }
    Glide.with(menuIcon.context)
      .load(menuIconDrawable)
      .transition(DrawableTransitionOptions.withCrossFade())
      .centerInside()
      .into(menuIcon)
  }

  private fun showMenuContent(show: Boolean = true) {
    setMenuIcon(show)
    val menuHeight = binding.root.resources.getDimension(R.dimen.menu_height)
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