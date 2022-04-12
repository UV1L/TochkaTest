package anton.android.tochkatest.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class HomeScreenViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val IS_DIALOG_SHOWN = "isDialogShown"
    }

    private val _isDialogShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData(IS_DIALOG_SHOWN)

    val isDialogShown: LiveData<Boolean> = _isDialogShown

    fun saveIsDialogShown(isShown: Boolean) {
        savedStateHandle.set(IS_DIALOG_SHOWN, isShown)
    }
}