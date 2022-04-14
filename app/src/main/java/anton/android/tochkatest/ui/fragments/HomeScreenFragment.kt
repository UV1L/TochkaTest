package anton.android.tochkatest.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.FragmentHomeBinding
import anton.android.tochkatest.ui.MainActivity
import anton.android.tochkatest.ui.base.BaseSaveableFragment
import anton.android.tochkatest.view_model.HomeScreenViewModel
import observeAllBooleans
import timber.log.Timber

class HomeScreenFragment : BaseSaveableFragment(),
    View.OnClickListener {

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: HomeScreenViewModel by viewModels()

    private var signOutAlertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.homeMenuButton.setOnClickListener(this)
        viewBinding.homeSignOutButton.setOnClickListener(this)
        viewBinding.homeSearchView.setOnClickListener(this)
        viewBinding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchDataChanged(newText) }
                return true
            }
        })
        observeAll()
    }

    override fun saveState(): Bundle {

        Timber.d("saveState")
        val bundle = Bundle()
        bundle.putBoolean(HomeScreenViewModel.IS_NAV_OPEN_TAG, viewBinding.root.isOpen)
        bundle.putBoolean(
            HomeScreenViewModel.IS_DIALOG_SHOWN_TAG,
            signOutAlertDialog?.isShowing ?: false
        )
        return bundle
    }

    override fun provideViewModel(): HomeScreenViewModel = viewModel

    override fun onDestroy() {
        super.onDestroy()

        _viewBinding = null
    }

    override fun onClick(view: View?) {

        when (view) {
            viewBinding.homeMenuButton -> openMenu()

            viewBinding.homeSignOutButton -> signOut()

            viewBinding.homeSearchView -> openEditor()
        }
    }

    private fun observeAll() {

        listOf(
            viewModel.isDialogShown to this::signOut,
            viewModel.isNavShown to this::openMenu,
            viewModel.isSignedOut to this::popBackOnAuthFragmentInclusive
        ).observeAllBooleans(viewLifecycleOwner)
    }

    private fun openMenu() = (activity as MainActivity).openDrawer()

    private fun signOut() {

        signOutAlertDialog = AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.are_you_sure_message))
            .setPositiveButton(getString(R.string.yes_string)) { _, _ ->
                viewModel.signOut(requireActivity().application)
            }
            .setNegativeButton(getString(R.string.no_string)) { _, _ -> }
            .create()
            .apply { show() }
    }

    private fun openEditor() {

        viewBinding.homeSearchView.isIconified = false
    }

    private fun popBackOnAuthFragmentInclusive() =
        findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToAuthFragment())
}