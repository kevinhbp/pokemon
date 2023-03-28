package id.co.app.types.ui


import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.types.databinding.FragmentTypesBinding
import id.co.app.types.viewModels.TypesViewModel
import id.co.app.nucocore.adapters.DefaultEmptyStateAdapter
import id.co.app.nucocore.adapters.pokemon.PokeTypeButtonAdapter
import id.co.app.nucocore.adapters.pokemon.PokeTypeHeaderAdapter
import id.co.app.nucocore.base.adapterdelegate.adapter.LoadingAdapter
import id.co.app.nucocore.components.dialog.showLoadingDialog
import id.co.app.nucocore.extension.pokemon.ColorPokemonBg
import id.co.app.nucocore.navigation.MainActNavi
import org.koin.androidx.viewmodel.ext.android.viewModel

class TypesFragment : BaseFragment<FragmentTypesBinding>() {

  private val typesViewModel: TypesViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(DefaultEmptyStateAdapter { })
      .add(LoadingAdapter())
      .add(PokeTypeHeaderAdapter())
      .add(PokeTypeButtonAdapter {
        typesViewModel.setSelectedType(it)
      })
      .build()
  }

  override fun provideBinding(): FragmentTypesBinding =
    FragmentTypesBinding.inflate(layoutInflater).apply { lifecycleOwner = viewLifecycleOwner }

  override fun setupView() {
    (requireActivity() as MainActNavi).showActionBar(true)
    setupRecyclerView()
  }

  override fun observeViewModel() {
    // Content
    typesViewModel.contentData.observe(viewLifecycleOwner) {
      mAdapter.submitList(it)
    }

    // Loading
    typesViewModel.loading.observe(viewLifecycleOwner) { showLoading ->
      requireActivity().showLoadingDialog(showLoading)
    }

    // Type
    typesViewModel.selectedType.observe(viewLifecycleOwner) {
      adaptBg(it)
    }
  }

  override fun loadData() {
    typesViewModel.start(
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

  private fun adaptBg(type: String) {
    val newColor= ColorPokemonBg.get(type)
    val defaultStateList = arrayOf(
      IntArray(1) { android.R.attr.state_enabled },
      IntArray(1) { -android.R.attr.state_enabled },
    )
    val colorList = IntArray(2).apply {
      this[0] = newColor
      this[1] = newColor
    }
    val view = binding.imageBackground
    view.imageTintList = ColorStateList(defaultStateList, colorList)
  }
}