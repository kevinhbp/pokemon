package id.co.app.nuco.splash.ui

import androidx.core.net.toUri
import id.co.app.nuco.databinding.FragmentSplashBinding
import id.co.app.nuco.main.ui.MainActivity
import id.co.app.nuco.splash.viewModels.SplashViewModel
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.navigation.MainActNavi
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

  private val splashViewModel: SplashViewModel by viewModel()

  override fun provideBinding(): FragmentSplashBinding {
    return FragmentSplashBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    (requireActivity() as MainActNavi).showActionBar(true)
    setStatusColor(true)
    setupButton()
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
  private fun setupButton() {
    binding.buttonPokedex.setOnClickListener {
      navigateTo(InternalDeepLink.HOME.toUri(), 0)
    }
  }
}