package anton.android.tochkatest.ui.base

import androidx.fragment.app.Fragment
import androidx.savedstate.SavedStateRegistry
import anton.android.tochkatest.utils.BaseViewModelProvider
import anton.android.tochkatest.view_model.base.BaseViewModel

abstract class BaseSaveableFragment : Fragment(),
    SavedStateRegistry.SavedStateProvider,
    BaseViewModelProvider {

    val TAG = this.javaClass.name
    private lateinit var viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()

        viewModel = provideViewModel()
        viewModel.setSavedStateProvider(TAG, this)
        viewModel.restoreState()
    }
}