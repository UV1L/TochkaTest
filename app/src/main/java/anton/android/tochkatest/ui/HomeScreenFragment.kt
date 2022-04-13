package anton.android.tochkatest.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.FragmentHomeBinding
import anton.android.tochkatest.ui.base.BaseSaveableFragment
import anton.android.tochkatest.utils.ApplicationState
import anton.android.tochkatest.view_model.HomeScreenViewModel
import com.firebase.ui.auth.AuthUI
import timber.log.Timber
import javax.inject.Inject

class HomeScreenFragment @Inject constructor(
    private val viewModel: HomeScreenViewModel
) : BaseSaveableFragment(viewModel),
    View.OnClickListener {

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        viewBinding.homeNavigation.setupWithNavController(findNavController())
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.homeMenuButton.setOnClickListener(this)
        viewBinding.homeSignOutButton.setOnClickListener(this)
        observeAll()
    }

    override fun onDestroy() {
        super.onDestroy()

        _viewBinding = null
    }

    override fun saveState(): Bundle {

        val bundle = Bundle()
        bundle.putBoolean(HomeScreenViewModel.IS_NAV_OPEN_TAG, viewBinding.root.isOpen)
        return bundle
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

//        homeScreenViewModel.saveNavState(viewBinding.root.isOpen)
        Timber.d("onSave")
    }

    override fun onClick(view: View?) {

        when (view) {
            viewBinding.homeMenuButton -> openMenu()

            viewBinding.homeSignOutButton -> viewModel.performOnSignOutClick()
        }
    }

    private fun observeAll() {

        viewModel.isDialogShown.observe(viewLifecycleOwner) { isShown ->
            if (isShown) signOut()
        }

        viewModel.isNavShown.observe(viewLifecycleOwner) { isOpen ->
            if (isOpen) openMenu()
        }
    }

    private fun openMenu() {
        viewBinding.root.open()
    }

    private fun signOut() {

        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.are_you_sure_message))
            .setPositiveButton(getString(R.string.yes_string)) { _, _ ->
                AuthUI.getInstance()
                    .signOut(requireContext())
                ApplicationState.currentUser = null
                findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToAuthFragment())
            }
            .setNegativeButton(getString(R.string.no_string)) { _, _ ->
                viewModel.performOnSignOutClick()
            }
            .create()
            .apply { show() }
    }
}