package anton.android.tochkatest.view_model.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistry

abstract class BaseViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var TAG: String? = null

    abstract fun restoreState()

    fun requireTag() = TAG!!

    fun setSavedStateProvider(key: String, provider: SavedStateRegistry.SavedStateProvider) {
        savedStateHandle.setSavedStateProvider(key, provider)
        TAG = key
    }

    interface BaseViewModelFactory<T : BaseViewModel> {
        fun create(savedStateHandle: SavedStateHandle): T
    }
}