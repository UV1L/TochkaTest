package anton.android.tochkatest.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import anton.android.tochkatest.BaseApplication
import anton.android.tochkatest.databinding.FragmentUserListBinding
import anton.android.tochkatest.ui.base.BaseSaveableFragment
import anton.android.tochkatest.ui.base.viewModel
import anton.android.tochkatest.ui.home.GithubUsersAdapter
import anton.android.tochkatest.view_model.HomeScreenViewModel
import anton.android.tochkatest.view_model.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest


class UserListFragment : BaseSaveableFragment() {

    private var _dataBinding: FragmentUserListBinding? = null
    private val dataBinding get() = _dataBinding!!

    private val viewModel: HomeScreenViewModel by viewModel {
        (requireActivity().application as BaseApplication).daggerComponent
            .homeScreenViewModelFactory.create(it)
    }
    private val args: UserListFragmentArgs by navArgs()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        GithubUsersAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _dataBinding = FragmentUserListBinding.inflate(inflater, container, false)
        setView()
        setListeners()

        return dataBinding.root
    }

    override fun saveState(): Bundle = Bundle()

    override fun provideViewModel(): BaseViewModel = viewModel

    private fun setView() {

        viewModel.searchDataChanged(args.query)
        dataBinding.userListRecycler.adapter = adapter
        dataBinding.userListRecycler.addItemDecoration(
            DividerItemDecoration(
                dataBinding.userListRecycler.context,
                DividerItemDecoration.VERTICAL
            )
        )
        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.users
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    private fun setListeners() {

        dataBinding.userListBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}