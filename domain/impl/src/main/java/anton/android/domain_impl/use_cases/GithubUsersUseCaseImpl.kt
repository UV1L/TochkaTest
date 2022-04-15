package anton.android.domain_impl.use_cases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import anton.android.domain_api.data_api.GithubUsersRepository
import anton.android.domain_api.entities.UserEntity
import anton.android.domain_api.use_cases.GithubUsersUseCase
import anton.android.domain_api.utils.Const.DEFAULT_PAGE_SIZE
import javax.inject.Inject

class GithubUsersUseCaseImpl @Inject constructor(
    private val repository: GithubUsersRepository
) : GithubUsersUseCase {

    override fun invoke(username: String): Pager<Int, UserEntity> {
        return Pager(
            PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
        ) {
            repository.getUserByName(username)
        }
    }
}