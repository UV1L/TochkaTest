package anton.android.data_impl

import anton.android.data_api.GithubUsersRepository
import anton.android.domain_api.home.entities.UserEntity
import kotlinx.coroutines.flow.Flow

class GithubUsersRepositoryImpl : GithubUsersRepository {

    override fun getAllUsers(): Flow<List<UserEntity>> {
        TODO("Not yet implemented")
    }
}