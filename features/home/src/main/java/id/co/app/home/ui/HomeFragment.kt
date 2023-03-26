package id.co.app.home.ui

import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.home.adapters.HomeWidgetAdapter
import id.co.app.home.databinding.FragmentHomeBinding
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nucocore.adapters.*
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.components.dialog.showLoadingDialog
import id.co.app.nucocore.extension.applyDefaultColor
import id.co.app.nucocore.extension.getParallaxListener
import id.co.app.nucocore.navigation.MainActNavi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
      .add(ActivityAdapter(object : ActivityListener {
        override fun doEditOrView(model: ActivityModel) {
          
        }

        override fun doDelete(model: ActivityModel) { }
      }))
      .add(DefaultEmptyStateAdapter { })
      .build()
  }

  override fun onCompleteDraw() {
    super.onCompleteDraw()
    (requireActivity() as MainActNavi).doCheckDataSync()
  }

  override fun provideBinding(): FragmentHomeBinding {
    return FragmentHomeBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    binding.viewModel = homeViewModel
    setupList()
    (requireActivity() as MainActNavi).showBottomNavigation()
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

  // --
  private fun setupList() {
    val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    binding.rvHome.apply {
      adapter = mAdapter
      layoutManager = mLayoutManager
    }

    binding.srHome.setProgressViewOffset(false, 0, 150)
    binding.srHome.applyDefaultColor()
    binding.srHome.setOnRefreshListener {
      setupSwipeRefresh()
    }

    binding.rvHome.getParallaxListener { _, alpha ->
      binding.layoutHomeActionbar.viewBackground.alpha = (1 - alpha)
      binding.imageBackground.alpha = alpha
    }
  }

  private fun setupSwipeRefresh() {
    lifecycleScope.launch {
      delay(400)
      binding.srHome.isRefreshing = false
    }

    homeViewModel.loadContent()
  }
}