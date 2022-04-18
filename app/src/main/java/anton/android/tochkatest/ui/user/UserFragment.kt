package anton.android.tochkatest.ui.user

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.navArgs
import anton.android.domain_api.use_cases.GithubRepositoriesUseCase
import anton.android.tochkatest.BaseApplication
import anton.android.tochkatest.databinding.FragmentUserBinding
import anton.android.tochkatest.view_model.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserFragment : Fragment() {

    private var _dataBinding: FragmentUserBinding? = null
    private val dataBinding get() = _dataBinding!!
    private val args: UserFragmentArgs by navArgs()

    private val viewModel: UserViewModel by viewModels {
        (requireActivity().application as BaseApplication).daggerComponent
            .viewModelsFactory()
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        GithubRepositoriesAdapter(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _dataBinding = FragmentUserBinding.inflate(inflater)
        setupView()

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setTransitionName(dataBinding.userFragmentAvatar, "avatar_${args.userId}")
        ViewCompat.setTransitionName(dataBinding.userFragmentName, "name_${args.userId}")
        observeAll()
    }

    override fun onDestroy() {
        super.onDestroy()

        _dataBinding = null
    }

    private fun setupView() {

        dataBinding.user = args.user
        dataBinding.viewmodel = viewModel
        dataBinding.userFragmentName.isSelected = true
        viewModel.setLoading()

        dataBinding.userFragmentRecycler.adapter = adapter
    }

    private fun observeAll() {

        viewModel.findRepositories(args.user.username)

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.repos
                .collectLatest { data ->
                    data?.let {
                        launch {
                            adapter.submitList(it)
                        }
                        viewModel.setLoaded()
                    }
                }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->

            with(dataBinding.userFragmentProgress) {
                this.visibility = if (loading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }
}