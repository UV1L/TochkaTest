package anton.android.tochkatest.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistry
import anton.android.tochkatest.utils.BaseViewModelProvider
import anton.android.tochkatest.view_model.base.BaseViewModel

abstract class BaseSaveableFragment : Fragment(),
    SavedStateRegistry.SavedStateProvider,
    BaseViewModelProvider {

    val TAG = this.javaClass.name
    private lateinit var viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = provideViewModel()
        viewModel.setSavedStateProvider(TAG, this)
        viewModel.restoreState()
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, arguments ?: Bundle()) {
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T =
            provider(handle) as T
    }
}