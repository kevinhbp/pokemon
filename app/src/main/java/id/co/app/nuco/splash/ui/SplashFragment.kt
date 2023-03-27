package id.co.app.nuco.splash.ui

import id.co.app.nuco.databinding.FragmentSplashBinding
import id.co.app.nuco.splash.viewModels.SplashViewModel
import id.co.app.nucocore.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

  private val splashViewModel: SplashViewModel by viewModel()

  override fun provideBinding(): FragmentSplashBinding {
    return FragmentSplashBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    setStatusColor(true)
  }

  override fun observeViewModel() {
    splashViewModel.splashModelLiveData.observe(viewLifecycleOwner) { model ->
      binding.model = model
    }
  }

  override fun loadData() {
    splashViewModel.start()
  }
}