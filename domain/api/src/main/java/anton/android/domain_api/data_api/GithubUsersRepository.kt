package anton.android.domain_api.data_api

import androidx.paging.PagingSource
import anton.android.domain_api.entities.RepositoryEntity
import anton.android.domain_api.entities.UserEntity
import anton.android.domain_api.utils.ResultWrapper
import retrofit2.HttpException

interface GithubUsersRepository {

    fun getUserByName(username: String): PagingSource<Int, UserEntity>

    suspend fun getRepositoriesForUser(username: String): Result<List<RepositoryEntity>>
}