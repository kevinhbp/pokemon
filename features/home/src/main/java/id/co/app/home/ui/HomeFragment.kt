package id.co.app.home.ui

import androidx.core.net.toUri
import id.co.app.home.adapters.HomeWidgetAdapter
import id.co.app.home.databinding.FragmentHomeBinding
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nucocore.adapters.DefaultEmptyStateAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.adapters.TextHeroAdapter
import id.co.app.nucocore.adapters.TextSubtitleAdapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.components.dialog.showLoadingDialog
import id.co.app.nucocore.navigation.MainActNavi
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  private val homeViewModel: HomeViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(TextHeroAdapter { })
      .add(TextSubtitleAdapter { })
      .add(HomeWidgetAdapter {
        try {
          navigateTo(it.toUri())
        } catch (e: Exception) {
          e.printStackTrace()
        }
      })
      .add(DefaultEmptyStateAdapter { })
      .build()
  }

  override fun provideBinding(): FragmentHomeBinding {
    return FragmentHomeBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    binding.viewModel = homeViewModel
    (requireActivity() as MainActNavi).showActionBar()
  }

  override fun observeViewModel() {
    // Content
    homeViewModel.contentData.observe(viewLifecycleOwner) {
      mAdapter.submitList(it)
    }

    // Loading
    homeViewModel.loading.observe(viewLifecycleOwner) { showLoading ->
      requireActivity().showLoadingDialog(showLoading)
    }
  }

  override fun loadData() {
    homeViewModel.loadContent()
  }
}