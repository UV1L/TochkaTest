package anton.android.tochkatest.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import anton.android.tochkatest.BaseApplication
import anton.android.tochkatest.R
import anton.android.tochkatest.databinding.FragmentHomeBinding
import anton.android.tochkatest.databinding.NavigationViewHeaderLayoutBinding
import anton.android.tochkatest.ui.base.BaseSaveableFragment
import anton.android.tochkatest.ui.base.viewModel
import anton.android.tochkatest.utils.ApplicationState
import anton.android.tochkatest.view_model.HomeScreenViewModel
import kotlinx.coroutines.flow.collectLatest
import observeAllBooleans
import timber.log.Timber

class HomeScreenFragment : BaseSaveableFragment(),
    View.OnClickListener {

    private var _dataBinding: FragmentHomeBinding? = null
    private val dataBinding get() = _dataBinding!!

    private var _navBinding: NavigationViewHeaderLayoutBinding? = null
    private val navBinding get() = _navBinding!!

    private val application: BaseApplication by lazy { requireActivity().application as BaseApplication }
    private val viewModel: HomeScreenViewModel by viewModel {
        application.daggerComponent.homeScreenViewModelFactory.create(it)
    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        GithubUsersPreviewAdapter(requireContext())
    }

    private var signOutAlertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _dataBinding = FragmentHomeBinding.inflate(inflater, container, false)

        setupView()
        setupNavigation()

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeAll()
    }

    override fun saveState(): Bundle {

        Timber.d("saveState")
        val bundle = Bundle()
        bundle.putBoolean(HomeScreenViewModel.IS_NAV_OPEN_TAG, isDrawerOpen())
        bundle.putBoolean(
            HomeScreenViewModel.IS_DIALOG_SHOWN_TAG,
            signOutAlertDialog?.isShowing ?: false
        )
        return bundle
    }

    override fun provideViewModel(): HomeScreenViewModel = viewModel

    override fun onDestroy() {
        super.onDestroy()

        _dataBinding = null
    }

    override fun onClick(view: View?) {

        when (view) {
            dataBinding.homeMenuButton -> openMenu()

            dataBinding.homeSignOutButton -> signOut()

            dataBinding.homeSearchView -> openEditor()
        }
    }

    private fun observeAll() {

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.users
                .collectLatest(adapter::submitData)
        }

        viewModel.query.observe(viewLifecycleOwner) {
            dataBinding.homeSearchView.setQuery(it, false)
        }

        listOf(
            viewModel.isDialogShown to this::signOut,
            viewModel.isNavShown to this::openMenu,
            viewModel.isSignedOut to this::popBackOnAuthFragmentInclusive
        ).observeAllBooleans(viewLifecycleOwner)
    }

    private fun openMenu() = dataBinding.homeDrawer.openDrawer(Gravity.LEFT)

    private fun isDrawerOpen() = _dataBinding?.homeDrawer?.isOpen ?: false

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

        dataBinding.homeSearchView.isIconified = false
    }

    private fun popBackOnAuthFragmentInclusive() {

        findNavController().navigate(HomeScreenFragmentDirections.actionHomeFragmentToAuthFragment())
        requireActivity().viewModelStore.clear()
    }

    private fun setupView() {

        dataBinding.homeUsersRecycler.adapter = adapter
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewmodel = viewModel
    }

    private fun setupNavigation() {

        dataBinding.homeNavigation.setupWithNavController(findNavController())
        val navHeader = dataBinding.homeNavigation.getHeaderView(0)
        _navBinding = NavigationViewHeaderLayoutBinding.bind(navHeader)
        navBinding.state = ApplicationState
        navBinding.navHeaderUsername.isSelected = true
    }

    private fun setupListeners() {

        dataBinding.homeMenuButton.setOnClickListener(this)
        dataBinding.homeSignOutButton.setOnClickListener(this)
        dataBinding.homeSearchView.setOnClickListener(this)
        dataBinding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                findNavController().navigate(
                    HomeScreenFragmentDirections.actionHomeFragmentToUserListFragment()
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchDataChanged(newText)
                }
                return true
            }
        })
    }
}