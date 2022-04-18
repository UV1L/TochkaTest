package anton.android.tochkatest.view_model

import android.app.Application
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import anton.android.domain_api.entities.RepositoryEntity
import anton.android.domain_api.entities.UserEntity
import anton.android.domain_api.use_cases.GithubRepositoriesUseCase
import anton.android.domain_api.use_cases.GithubUsersUseCase
import anton.android.tochkatest.R
import anton.android.tochkatest.utils.ApplicationState
import anton.android.tochkatest.view_model.base.BaseViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

class HomeScreenViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val githubUsersUseCase: GithubUsersUseCase,
) : BaseViewModel(savedStateHandle) {

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): HomeScreenViewModel
    }

    companion object {
        const val IS_DIALOG_SHOWN_TAG = "isDialogShown"
        const val IS_NAV_OPEN_TAG = "navigationViewTag"
        const val IS_SIGNED_OUT = "isSignedOut"
        const val IS_ERROR = "isError"

        @JvmStatic
        @BindingAdapter("bind:imageUrlCircle")
        fun loadAvatarCircle(imageView: ImageView, url: String) {
            loadByPicasso(url)
                .transform(CropCircleTransformation())
                .placeholder(R.drawable.rounded_view)
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun loadAvatar(imageView: ImageView, url: String) {
            loadByPicasso(url)
                .placeholder(R.drawable.rounded_field)
                .into(imageView)
        }

        private fun loadByPicasso(url: String): RequestCreator {
            return Picasso.get()
                .load(url)
        }
    }

    private var searchJob: Job? = null

    private val _isDialogShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_DIALOG_SHOWN_TAG).apply { value = false }

    private val _isNavShown: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_NAV_OPEN_TAG).apply { value = false }

    private val _isSignedOut: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean?>(IS_SIGNED_OUT).apply { value = false }

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: LiveData<String> = _query.asLiveData()

    val isDialogShown: LiveData<Boolean> = _isDialogShown
    val isNavShown: LiveData<Boolean> = _isNavShown
    val isSignedOut: LiveData<Boolean> = _isSignedOut

    val users: StateFlow<PagingData<UserEntity>> =
        _query.map(::getPager)
            .flatMapLatest { it.flow }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                PagingData.empty()
            )

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

        searchJob?.cancel()
        if (newString.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(600)
                _query.tryEmit(newString)
            }
        } else {
            _query.value = ""
        }
    }

    private fun getPager(username: String): Pager<Int, UserEntity> {
        return githubUsersUseCase.invoke(username)
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