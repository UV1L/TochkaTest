package anton.android.tochkatest.ui.user

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.navArgs
import anton.android.tochkatest.BaseApplication
import anton.android.tochkatest.databinding.FragmentUserBinding
import anton.android.tochkatest.ui.base.BaseSaveableFragment
import anton.android.tochkatest.ui.base.viewModel
import anton.android.tochkatest.ui.home.GithubUsersAdapter
import anton.android.tochkatest.view_model.HomeScreenViewModel
import anton.android.tochkatest.view_model.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserFragment : BaseSaveableFragment() {

    private var _dataBinding: FragmentUserBinding? = null
    private val dataBinding get() = _dataBinding!!
    private val args: UserFragmentArgs by navArgs()

    private val viewModel: HomeScreenViewModel by viewModel {
        (requireActivity().application as BaseApplication).daggerComponent
            .homeScreenViewModelFactory.create(it)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        GithubRepositoriesAdapter(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _dataBinding = FragmentUserBinding.inflate(inflater)
        setView()
        observeAll()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setTransitionName(dataBinding.userFragmentAvatar, "avatar_${args.userId}")
        ViewCompat.setTransitionName(dataBinding.userFragmentName, "name_${args.userId}")
    }

    override fun onDestroy() {
        super.onDestroy()

        _dataBinding = null
    }

    override fun saveState(): Bundle = Bundle()

    override fun provideViewModel(): BaseViewModel = viewModel

    private fun setView() {
        dataBinding.user = args.user
        dataBinding.viewmodel = viewModel

        dataBinding.userFragmentRecycler.adapter = adapter

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.repos
                .collectLatest { data ->
                    data?.let {
                        launch {
                            adapter.submitList(it)
                        }
                        viewModel.setReposReady()
                    }
                }
        }

        viewModel.areReposReady.observe(viewLifecycleOwner) {
            dataBinding.userFragmentProgress.visibility = View.GONE
        }
    }

    private fun observeAll() {
        viewModel.findRepositories(args.user.username)
    }
}