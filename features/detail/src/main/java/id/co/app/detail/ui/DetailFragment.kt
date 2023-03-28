package id.co.app.detail.ui


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.detail.databinding.FragmentDetailBinding
import id.co.app.detail.viewModels.DetailViewModel
import id.co.app.nucocore.adapters.DefaultEmptyStateAdapter
import id.co.app.nucocore.adapters.pokemon.PokeInfo1Adapter
import id.co.app.nucocore.adapters.pokemon.PokeInfo2Adapter
import id.co.app.nucocore.adapters.pokemon.PokeInfo3Adapter
import id.co.app.nucocore.base.adapterdelegate.adapter.LoadingAdapter
import id.co.app.nucocore.navigation.MainActNavi
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

  private val detailViewModel: DetailViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(DefaultEmptyStateAdapter { })
      .add(LoadingAdapter())
      .add(PokeInfo1Adapter())
      .add(PokeInfo2Adapter())
      .add(PokeInfo3Adapter())
      .build()
  }

  override fun provideBinding(): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater).apply { lifecycleOwner = viewLifecycleOwner }

  override fun setupView() {
    (requireActivity() as MainActNavi).showActionBar(true)
    setupRecyclerView()
  }

  override fun observeViewModel() {
    // Content
    detailViewModel.contentData.observe(viewLifecycleOwner) {
      mAdapter.submitList(it)
    }
  }

  override fun loadData() {
    detailViewModel.start(
      stringCallback = { id -> requireActivity().getString(id) },
      dimenCallback = { id -> requireActivity().resources.getDimension(id).toInt() }
    )
  }

  /// ---
  private fun setupRecyclerView() {
    val mRv = binding.rvContent
    val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    mRv.apply {
      adapter = mAdapter
      layoutManager = mLayoutManager
    }
  }
}