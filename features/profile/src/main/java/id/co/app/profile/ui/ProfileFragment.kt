package id.co.app.profile.ui


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.base.adapterdelegate.CompositeAdapter
import id.co.app.nucocore.R
import id.co.app.nucocore.adapters.ButtonPrimaryDestructiveAdapter
import id.co.app.nucocore.adapters.DefaultMenuAdapter
import id.co.app.nucocore.adapters.SpaceAdapter
import id.co.app.nucocore.base.BaseFragment
import id.co.app.nucocore.components.dialog.showConfirmationDialog
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.navigation.MainActNavi
import id.co.app.profile.adapters.ProfileHeaderAdapter
import id.co.app.profile.databinding.FragmentProfileBinding
import id.co.app.profile.viewModels.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

  private val profileViewModel: ProfileViewModel by viewModel()

  private val mAdapter by lazy {
    CompositeAdapter.Builder()
      .add(SpaceAdapter())
      .add(ProfileHeaderAdapter())
      .add(DefaultMenuAdapter {
        executeNavTarget(it.target)
      })
      .add(ButtonPrimaryDestructiveAdapter {
        executeNavTarget(it.target)
      })
      .build()
  }

  override fun provideBinding(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater).apply { lifecycleOwner = viewLifecycleOwner }

  override fun setupView() {
    val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    binding.rvProfile.apply {
      adapter = mAdapter
      layoutManager = mLayoutManager
    }
  }

  override fun observeViewModel() {
    profileViewModel.contentLiveData.observe(viewLifecycleOwner) {
      mAdapter.submitList(it)
    }
  }

  override fun loadData() {
    profileViewModel.loadData()
  }

  // --
  private fun executeNavTarget(target: String) {

  }
}