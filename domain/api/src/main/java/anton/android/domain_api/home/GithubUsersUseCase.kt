package anton.android.domain_api.home

import anton.android.domain_api.home.entities.UserEntity
import kotlinx.coroutines.flow.Flow

fun interface GithubUsersUseCase {
    fun execute(): Flow<List<UserEntity>>
}