package anton.android.data_api

import anton.android.domain_api.home.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {

    fun getAllUsers(): Flow<List<UserEntity>>
}