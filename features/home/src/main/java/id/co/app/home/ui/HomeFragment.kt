package id.co.app.home.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.home.databinding.FragmentHomeBinding
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nucocore.R
import id.co.app.nucocore.adapters.DefaultEmptyStateAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.adapters.pokemon.PokeCardAdapter
import id.co.app.nucocore.adapters.pokemon.PokeHeaderAdapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.base.RecyclerViewPagination
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.components.dialog.showLoadingDialog
import id.co.app.nucocore.extension.toDp
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  private val homeViewModel: HomeViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(DefaultEmptyStateAdapter { })
      .add(PokeHeaderAdapter())
      .add(PokeCardAdapter { })
      .build()
  }

  override fun provideBinding(): FragmentHomeBinding {
    return FragmentHomeBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    binding.viewModel = homeViewModel
    homeViewModel.setTopSpace(requireActivity().resources.getDimension(R.dimen.action_bar_height).toInt().toDp())
    setupRecyclerView()
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

  ///
  private val paginationListener by lazy {
    RecyclerViewPagination(
      recyclerView = binding.rvContent,
      isLoading = {
        homeViewModel.loading.value ?: false
      },
      loadMore = {
        homeViewModel.loadContent()
      },
      onLast = { false }
    ).apply {
      threshold = 2
    }
  }

  private fun setupRecyclerView() {
    val mRv = binding.rvContent
    val mSr = binding.srContent
    val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    mRv.apply {
      adapter = mAdapter
      layoutManager = mLayoutManager
      addOnScrollListener(paginationListener)
    }
    mSr.setOnRefreshListener {
      paginationListener.resetCurrentPage()
      homeViewModel.resetPage()
      homeViewModel.loadContent()
    }
  }
}