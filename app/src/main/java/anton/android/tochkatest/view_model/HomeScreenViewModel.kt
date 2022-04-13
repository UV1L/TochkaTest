package anton.android.tochkatest.view_model

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import anton.android.tochkatest.view_model.base.BaseViewModel

class HomeScreenViewModel(private val savedStateHandle: SavedStateHandle) :
    BaseViewModel(savedStateHandle) {

    companion object {
        const val IS_DIALOG_SHOWN_TAG = "isDialogShown"
        const val IS_NAV_OPEN_TAG = "navigationViewTag"
    }

    private val _isDialogShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_DIALOG_SHOWN_TAG).apply { value = false }

    private val _isNavShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_NAV_OPEN_TAG).apply { value = false }

    val isDialogShown: LiveData<Boolean> = _isDialogShown
    val isNavShown: LiveData<Boolean> = _isNavShown

    init {
        val savedBundle = savedStateHandle.get<Bundle>(requireTag())
        if (savedBundle != null) {
            if (savedBundle.containsKey(IS_NAV_OPEN_TAG)) {

                val isOpen = savedBundle.getBoolean(IS_NAV_OPEN_TAG)
                savedStateHandle.set(IS_NAV_OPEN_TAG, isOpen)
            }
        }
    }

    fun saveNavState(state: Boolean) {
        savedStateHandle.set(IS_NAV_OPEN_TAG, state)
    }

    fun performOnSignOutClick() {
        savedStateHandle.set(IS_DIALOG_SHOWN_TAG, !isDialogShown.value!!)
    }
}