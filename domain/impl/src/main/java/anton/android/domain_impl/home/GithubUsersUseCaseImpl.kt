package anton.android.domain_impl.home

import anton.android.data_api.GithubUsersRepository
import anton.android.domain_api.home.GithubUsersUseCase
import anton.android.domain_api.home.entities.UserEntity
import kotlinx.coroutines.flow.Flow

class GithubUsersUseCaseImpl(
    private val repository: GithubUsersRepository
) : GithubUsersUseCase {

    override fun execute(): Flow<List<UserEntity>> {
        return repository.getAllUsers()
    }
}