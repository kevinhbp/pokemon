package id.co.app.nuco.main.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import id.co.app.nuco.R.id
import id.co.app.nuco.R.string
import id.co.app.nuco.databinding.ActivityMainBinding
import id.co.app.nuco.main.viewModels.MainState
import id.co.app.nuco.main.viewModels.MainViewModel
import id.co.app.nuco.splash.ui.SplashFragmentDirections
import id.co.app.nucocore.components.blocker.BlockerViewModel
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel
import id.co.app.nucocore.domain.entities.row.BlockerDefaultModel.Companion.getInvalidConnectionBlocker
import id.co.app.nucocore.extension.makeStatusBarTransparent
import id.co.app.nucocore.extension.showToastInfo
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
        showToastInfo(getString(string.exit_confirm), Toast.LENGTH_LONG)
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
      add(id.login_fragment)
      add(id.home_fragment)
    }
    val destinationId = nav.currentBackStackEntry?.destination?.id ?: -1
    return preventSingleTouchExitFragments.contains(destinationId)
  }

  private fun onPreventExitPage(): Boolean {
    val nav = getNavController()
    val fragmentList = listOf(
      id.splash_fragment
    )
    val destinationId = nav.currentBackStackEntry?.destination?.id ?: -1
    return fragmentList.contains(destinationId)
  }

  private fun onOverrideBackPressedPage(): Boolean {
    val nav = getNavController()
    val fragmentList = listOf(
      id.pnd_fragment, id.na_fragment, id.dp_fragment, id.so_fragment
    )
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
  override fun navigateSplashToHome() {
    Log.d(LOG_TAG, "navigate: Splash to Home")
    getNavController()
      .navigate(SplashFragmentDirections.actionSplashScreenToHome())
  }
  // --
  private fun setupView() {
    binding.blockerViewModel = blockerViewModel
    binding.executePendingBindings()
  }

  private fun observeViewModel() {
    viewModel.mainStateLiveData.observe(this) { state ->
      when (state) {
        MainState.SHOW_BLOCKER_CONNECTION -> {
          showBlockerView(getInvalidConnectionBlocker())
        }
        MainState.REDIRECT_HOME -> {
          navigateSplashToHome()
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
  override fun doSplashCheck() {
    viewModel.splashAccessCheckup("")
  }

  override fun showActionBar(show: Boolean) {

  }

  override fun showBlockerView(model: BlockerDefaultModel) {
    blockerViewModel.showWithModel(model)
  }

  private fun onTargetChanged(it: String) {
    if (it == InternalDeepLink.RE_CONNECT) {
      doSplashCheck()
      return
    }
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
}