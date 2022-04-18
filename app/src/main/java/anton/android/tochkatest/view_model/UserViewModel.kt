package anton.android.tochkatest.view_model

import androidx.lifecycle.*
import anton.android.domain_api.entities.RepositoryEntity
import anton.android.domain_api.use_cases.GithubRepositoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class UserViewModel @Inject constructor(
    private val githubRepositoriesUseCase: GithubRepositoriesUseCase,
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory @Inject constructor(
        viewModelProvider: Provider<UserViewModel>
    ) : ViewModelProvider.Factory {
        private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
            UserViewModel::class.java to viewModelProvider
        )

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return providers[modelClass]!!.get() as T
        }
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private val _repos: MutableStateFlow<List<RepositoryEntity>?> = MutableStateFlow(null)
    val repos = _repos.asStateFlow()

    fun setLoading() {
        _isLoading.postValue(true)
    }

    fun setLoaded() {
        _isLoading.postValue(false)
    }

    fun findRepositories(username: String) {

        viewModelScope.launch {
            githubRepositoriesUseCase.invoke(username)
                .onEach { it.sortReposResult() }
                .collect()
        }
    }

    private fun Result<List<RepositoryEntity>>.sortReposResult() {

        this.onSuccess {
            _repos.tryEmit(it)
        }.onFailure {
            _error.postValue(true)
        }
    }
}