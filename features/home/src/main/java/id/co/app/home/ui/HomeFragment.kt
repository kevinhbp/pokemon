package id.co.app.home.ui

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import id.co.app.home.databinding.FragmentHomeBinding
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nucocore.R
import id.co.app.nucocore.adapters.DefaultEmptyStateAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.adapters.pokemon.PokeCardAdapter
import id.co.app.nucocore.adapters.pokemon.PokeHeaderAdapter
import id.co.app.nucocore.adapters.pokemon.PokeInfo1Adapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.base.RecyclerViewPagination
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.components.dialog.showLoadingDialog
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.extension.applyDefaultColor
import id.co.app.nucocore.extension.debouncer1
import id.co.app.nucocore.extension.initItemVisibleListenerLinearLayout
import id.co.app.nucocore.extension.toDp
import id.co.app.nucocore.navigation.MainActNavi
import id.co.app.nucocore.singleton.DataSingleton
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  private val homeViewModel: HomeViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(DefaultEmptyStateAdapter { })
      .add(PokeHeaderAdapter())
      .add(PokeCardAdapter {
        homeViewModel.setPokemon(it)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
      })
      .build()
  }

  private val mAdapterDetail by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(DefaultEmptyStateAdapter { })
      .add(PokeInfo1Adapter())
      .build()
  }

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

  private var bottomSheetState: Int = -1

  override fun provideBinding(): FragmentHomeBinding {
    return FragmentHomeBinding.inflate(layoutInflater)
      .apply { lifecycleOwner = viewLifecycleOwner }
  }

  override fun setupView() {
    (requireActivity() as MainActNavi).showActionBar(true)
    binding.viewModel = homeViewModel
    homeViewModel.topSpace =
      requireActivity().resources.getDimension(R.dimen.action_bar_height).toInt()
    setupRecyclerView()
    setupBottomSheet()
    setupRecyclerViewDetail()
    setupBackPressedEvent()
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

    // Detail
    homeViewModel.contentDataDetail.observe(viewLifecycleOwner) {
      mAdapterDetail.submitList(it)
    }
  }

  override fun loadData() {
    homeViewModel.loadContent()
  }

  ///
  private fun setupRecyclerView() {
    val mRv = binding.rvContent
    val mSr = binding.srContent
    val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    mRv.apply {
      adapter = mAdapter
      layoutManager = mLayoutManager
    }
    mSr.applyDefaultColor()
    mSr.setOnRefreshListener {
      homeViewModel.refresh()
    }
    mRv.initItemVisibleListenerLinearLayout { _, _, progress ->
      if (progress == 100) {
        homeViewModel.loadContent()
      }
    }
  }

  private fun setupBottomSheet() {
    bottomSheetBehavior = BottomSheetBehavior.from(binding.pokemonOverview.bsDetail)
    bottomSheetBehavior.isDraggable = false
    bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
        onBottomSheetSlide(slideOffset)
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        onBottomSheetStateChanged(newState)
      }
    })

    val moreDetailButton = binding.pokemonOverview.buttonMoreDetail
    moreDetailButton.setOnClickListener {
      if (DataSingleton.getDefaultInstance().selectedPokemon != null) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        debouncer1(130) {
          navigateTo(InternalDeepLink.DETAIL.toUri())
        }
      }
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setupRecyclerViewDetail() {
    val mRv = binding.pokemonOverview.rvContent
    val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    mRv.apply {
      adapter = mAdapterDetail
      layoutManager = mLayoutManager
      isNestedScrollingEnabled = true
      setItemViewCacheSize(10)
      hasFixedSize()
    }
  }

  private fun onBottomSheetSlide(slideOffset: Float) {
    val content = binding.pokemonOverview.wrapperExpandedContent
    val shadow = binding.pokemonOverview.viewShadow
    content.alpha = slideOffset
    shadow.alpha = slideOffset
  }

  private fun onBottomSheetStateChanged(newState: Int) {
    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
      bottomSheetBehavior.isDraggable = true
      (requireActivity() as MainActNavi).showActionBar(false)
      setStatusColor(false)
    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
      bottomSheetBehavior.isDraggable = false
      (requireActivity() as MainActNavi).showActionBar(true)
      setStatusColor(true)
    }
    bottomSheetState = newState
  }

  private fun isBottomSheetExpanded(): Boolean = bottomSheetState == BottomSheetBehavior.STATE_EXPANDED

  private fun setupBackPressedEvent() {
    requireActivity()
      .onBackPressedDispatcher
      .addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          if (isBottomSheetExpanded()) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            return
          }
          findNavController().navigateUp()
        }
      })
  }
}