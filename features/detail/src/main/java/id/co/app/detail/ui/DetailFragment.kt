package id.co.app.detail.ui


import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.detail.databinding.FragmentDetailBinding
import id.co.app.detail.viewModels.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

  private val detailViewModel: DetailViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .build()
  }

  override fun provideBinding(): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater).apply { lifecycleOwner = viewLifecycleOwner }

  override fun setupView() {

  }

  override fun observeViewModel() {

  }

  override fun loadData() {
    detailViewModel.loadData()
  }
}