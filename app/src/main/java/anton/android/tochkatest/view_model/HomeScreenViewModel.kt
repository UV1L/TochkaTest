package anton.android.tochkatest.view_model

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import anton.android.tochkatest.utils.ApplicationState
import anton.android.tochkatest.view_model.base.BaseViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val savedStateHandle: SavedStateHandle) :
    BaseViewModel(savedStateHandle) {

    companion object {
        const val IS_DIALOG_SHOWN_TAG = "isDialogShown"
        const val IS_NAV_OPEN_TAG = "navigationViewTag"
        const val IS_SIGNED_OUT = "isSignedOut"
    }

    private var searchJob: Job? = null

    private val _isDialogShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_DIALOG_SHOWN_TAG).apply { value = false }

    private val _isNavShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_NAV_OPEN_TAG).apply { value = false }

    private val _isSignedOut: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_SIGNED_OUT).apply { value = false }

    val isDialogShown: LiveData<Boolean> = _isDialogShown
    val isNavShown: LiveData<Boolean> = _isNavShown
    val isSignedOut: LiveData<Boolean> = _isSignedOut

    override fun restoreState() {

        val savedBundle = savedStateHandle.get<Bundle>(requireTag())
        savedBundle?.changeStateFor(IS_DIALOG_SHOWN_TAG, IS_NAV_OPEN_TAG)
    }

    fun signOut(application: Application) {

        val task = AuthUI.getInstance()
            .signOut(application)
        task.addOnCompleteListener { _isSignedOut.value = true }
        ApplicationState.currentUser = null
    }

    fun searchDataChanged(newString: String) {

        if (newString.isNotEmpty()) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(600)

            }
        }
    }

    private fun Bundle.changeStateFor(vararg tags: String) {

        tags.forEach { tag ->
            if (this.containsKey(tag)) {
                val flag = this.getBoolean(tag)
                savedStateHandle.set(tag, flag)
            }
        }
    }
}