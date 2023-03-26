package id.co.app.nuco.splash.ui

import android.util.Log
import id.co.app.nuco.databinding.FragmentSplashBinding
import id.co.app.nuco.splash.viewModels.SplashViewModel

import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.navigation.MainActNavi
import id.co.app.nucocore.singleton.LOG_TAG
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

  private val splashViewModel: SplashViewModel by viewModel()

  override fun provideBinding(): FragmentSplashBinding {
    return FragmentSplashBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    setStatusColor(false)
    (requireActivity() as MainActNavi).showBottomNavigation(false)
  }

  override fun observeViewModel() {
    splashViewModel.splashModelLiveData.observe(viewLifecycleOwner) { model ->
      binding.model = model
    }
  }

  override fun loadData() {
    splashViewModel.start()
    Log.d(LOG_TAG, "Splash Load Data -> doSplashCheck()")
    (requireActivity() as MainActNavi).doSplashCheck()
  }
}