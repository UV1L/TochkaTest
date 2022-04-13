package anton.android.tochkatest.ui.base

import androidx.fragment.app.Fragment
import androidx.savedstate.SavedStateRegistry
import anton.android.tochkatest.view_model.base.BaseViewModel

abstract class BaseSaveableFragment(private val viewModel: BaseViewModel) : Fragment(),
    SavedStateRegistry.SavedStateProvider {

    val TAG = this.javaClass.name

    override fun onStart() {
        super.onStart()

        viewModel.setSavedStateProvider(TAG, this)
    }
}