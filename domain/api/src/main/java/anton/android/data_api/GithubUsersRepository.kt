package anton.android.data_api

import anton.android.domain_api.home.entities.UserEntity
import anton.android.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {

    fun getAllUsers(): Flow<ResultWrapper<List<UserEntity>, out Throwable>>
}