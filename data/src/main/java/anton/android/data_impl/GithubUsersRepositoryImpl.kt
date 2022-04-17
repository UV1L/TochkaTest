package anton.android.data_impl

import androidx.paging.PagingSource
import anton.android.data_impl.models.repositoryEntity
import anton.android.data_impl.network.GithubService
import anton.android.data_impl.network.GithubUsersPagingSource
import anton.android.data_impl.network.ServiceProvider
import anton.android.domain_api.data_api.GithubUsersRepository
import anton.android.domain_api.entities.RepositoryEntity
import anton.android.domain_api.entities.UserEntity
import anton.android.domain_api.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.HttpException
import safeApiCallAsync
import javax.inject.Inject

class GithubUsersRepositoryImpl @Inject constructor(
    serviceProvider: ServiceProvider,
    baseClient: OkHttpClient
) : GithubUsersRepository {

    init {
        serviceProvider.setClient(baseClient)
    }

    private val githubService: GithubService by lazy {
        serviceProvider.provideService(GithubService::class.java)
    }

    override fun getUserByName(username: String): PagingSource<Int, UserEntity> {
        return GithubUsersPagingSource(githubService, username)
    }

    override suspend fun getRepositoriesForUser(username: String): Result<List<RepositoryEntity>> {

        val response = githubService.getRepositoriesForUser(username)

        return if (response.isSuccessful) {
            Result.success(
                response.body()!!
                    .map { it.repositoryEntity() }
            )
        } else {
            Result.failure(Throwable(response.message()))
        }
    }
}

