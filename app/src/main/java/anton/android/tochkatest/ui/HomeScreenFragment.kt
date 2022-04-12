package anton.android.tochkatest.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.FragmentHomeBinding
import anton.android.tochkatest.utils.ApplicationState
import anton.android.tochkatest.view_model.HomeScreenViewModel
import com.firebase.ui.auth.AuthUI
import timber.log.Timber

class HomeScreenFragment : Fragment(),
    View.OnClickListener {

    companion object {
        const val IS_NAV_OPEN_TAG = "navigationViewTag"
    }

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var signOutAlertDialog: AlertDialog? = null
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var isOpen = false
        if (savedInstanceState != null) {
            isOpen = savedInstanceState.getBoolean(IS_NAV_OPEN_TAG)
        }
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        viewBinding.homeNavigation.setupWithNavController(findNavController())
        if (isOpen) openMenu()
        homeScreenViewModel.isDialogShown.observe(viewLifecycleOwner) { isShown ->
            if (isShown) signOut()
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.homeMenuButton.setOnClickListener(this)
        viewBinding.homeSignOutButton.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        _viewBinding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_NAV_OPEN_TAG, viewBinding.root.isOpen)
        homeScreenViewModel.saveIsDialogShown(signOutAlertDialog?.isShowing ?: false)
        Timber.d("onSave")
    }

    override fun onClick(view: View?) {

        when (view) {
            viewBinding.homeMenuButton -> openMenu()

            viewBinding.homeSignOutButton -> signOut()
        }
    }

    private fun openMenu() {
        viewBinding.root.open()
    }

    private fun signOut() {

        signOutAlertDialog = AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.are_you_sure_message))
            .setPositiveButton(getString(R.string.yes_string)) { _, _ ->
                AuthUI.getInstance()
                    .signOut(requireContext())
                ApplicationState.currentUser = null
                findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToAuthFragment())
            }
            .setNegativeButton(getString(R.string.no_string)) { _, _ -> }
            .create()
            .apply { show() }
    }
}