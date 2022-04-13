package anton.android.tochkatest.view_model.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistry

abstract class BaseViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var TAG: String? = null

    fun requireTag() = TAG!!

    fun setSavedStateProvider(key: String, provider: SavedStateRegistry.SavedStateProvider) {
        savedStateHandle.setSavedStateProvider(key, provider)
        TAG = key
    }
}